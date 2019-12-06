import tensorflow.compat.v1 as tf
tf.disable_v2_behavior()
#import tensorflow as tf
import numpy as np
import random
from math import pow

import os

# Ohter import
import sys

eps = 0.9
v = np.full((1,360),3)
#bidon = np.vstack([v])
bidon = v


class DQNAgent(object):
    """docstring forDQNAgent."""
    #sess = tf.Session()

    def __init__(self, input_dim = 362 ):

        #placeholder pour l'entrée

        self.sess = None
        self.input_dim = input_dim
        self.nb_actions = 3
        self.gamma = 0.99
        self.input = tf.placeholder(tf.float32 , shape = [None,self.input_dim] , name = 'input' )
        self.layer1 = tf.layers.dense(self.input , 150 ,activation=tf.nn.relu)
        self.layer2 = tf.layers.dense(self.layer1 , 50 , activation=tf.nn.relu)
        self.prediction = tf.layers.dense(self.layer2 ,self.nb_actions)
        #tf.identity(self.predictions, name="predictions")

        #placeholder pour la target
        self.Q_target = tf.placeholder(tf.float32 , shape = [None ] , name = 'target_training')

        #placeholder pour l'action
        self.actions = tf.placeholder(shape=[None], dtype=tf.int32, name="actions")

        #méthode d'optimisation
        self.optimizer = tf.train.AdamOptimizer()


        #----------------------------Partie Sauvgarde--------------------------------------------


        # Used to save the mode
        self.checkpoint_dir = os.path.join("./", "checkpoints")
        self.checkpoint_path = os.path.join(self.checkpoint_dir, "model")
        # Load a previous checkpoint if we find one
        self.latest_checkpoint = tf.train.latest_checkpoint(self.checkpoint_dir)

        if not os.path.exists(self.checkpoint_dir):
            os.makedirs(self.checkpoint_dir)

        self.saver  = tf.train.Saver()
        """
        if os.path.exists(self.checkpoint_dir):

            tf.reset_default_graph()
            self.checkpoint = os.path.join("./checkpoints", "model")
            self.new_saver = tf.train.import_meta_graph(self.checkpoint + '.meta')

            self.new_saver.restore(self.sess, self.checkpoint)
        """




        #----------------------------------------------------------------------






    def predict(self ,input):

        with sefl.sess :
            return self.sess.run(self.prediction , feed_dict = {self.input : input})


    def takeActionMax(tab):
        action = 0
        for i in range(len(tab[0])):
            if tab[0][action] < tab[0][i] :
                action = i

        return action


    def pickAction(self,state , eps,env):
        #les 3 action sont 0,1 ou 2
        #plus eps est grand on fait de l'exploitation
        b = random.random()
        if eps >= b  :
            #on prend une action aux hasard
            action  = random.randint(0,2)

        else :
            features_vector = env.make_feature_vector(state)
            #features_vector = state + position prise sur le marché
            result = self.predict(features_vector)

            #on prend l'action qui a la plus grande recompense

            action = self.takeActionMax(result)

        #l'agent effectue  l'action dans l'environement
        env.doAction(action,state)

        return action


    #calcule du coût / forme récursive de l'équation de Belman
    def model_loss(self,tf_states , mask , Q_target) :

        a = pow(self.predict(tf_states) - Q_target,2)
        return a*mask



    def train_model(self,states,actions,rewards,next_st,ses):
        print("entrainement")
        taille_liste = len(next_st)

        tf_states = np.vstack(states)
        tf_rewards = np.vstack(rewards)
        tf_next_states = np.vstack(next_st)
        tf_actions = np.vstack(actions)

        losses = []

        #on fabrique le vecteur composé uniquement des valeurs max de la Q_target pour  chaque transition
        Q_stp1_temp = self.predict(tf_next_states)
        Q_stp1 = []
        for t in range(len(Q_stp1_temp)):
            Q_stp1.append(np.max(Q_stp1_temp[t]))

        Q_stp1 = np.vstack(Q_stp1)

        # Q_target = r+ gamma*max(q_fontion) //équation de Belman
        Q_targets = np.vstack(np.max(Q_stp1)*(self.gamma)+tf_rewards)

        taille_batch = 32



        for b in range(0,taille_liste,taille_batch):


            if taille_batch + b < taille_liste :
                taille_nouv_batch =  taille_batch
            else :
                taille_nouv_batch = taille_batch - b

            #on sélectionne un batch de 32 valeurs
            tf_states_b = tf_states[b:taille_nouv_batch]
            tf_states_b = np.vstack(tf_states_b)

            tf_actions_b = tf_actions[b:taille_nouv_batch]
            tf_actions_b = np.vstack(tf_actions_b)

            Q_targets_b = Q_targets[b:taille_nouv_batch]
            Q_targets_b  = np.vstack(Q_targets_b)

            loss = self.model_loss(tf_states_b , tf_actions_b , Q_targets_b)
            loss = tf.reduce_mean(loss)
            train_op  = self.optimizer.minimize(loss)

            feed_dict = {self.input : tf_states_b , self.Q_target : Q_targets_b , self.actions : tf_actions_b }
            ops = [train_op , loss ]

            nouv_loss = self.sess.run(ops , feed_dict)

            losses.append(nouv_loss)
            print("la moyenne des erreur sont ", sum(losses)/len(losses))


        saver.save(tf.get_default_session(), checkpoint_path)


        #nb_episode = nb de ligne de l'env -1
    def training(self,env , nb_episode = 1188372):

        with tf.Session().as_default() as sess :
            sess.run(tf.global_variables_initializer())



            eps = 1.0
            states = []
            rewards = []
            next_states = []

            #dans action il y a une liste (simple , pas une liste comme les prédictions) avec deux "0" et un "1"
            actions = []
            reward_mean = []


            #nb_line = 1188372

            st = next(env.market)
            for p in range(len(st)):
                st[p] = float(st[p])

            for i in range(nb_episode):
            #récupérer l'état de l'agent

                act = self.pickAction(st , eps,env)


                reward =  env.account


                st2 = next(env.market)
                st2 = env.make_feature_vector(st2)



                mask = np.zeros((1,self.nb_actions))
                mask[0][act] = 1.0

                #on transorme les string en float


                index = random.randint(0,len(states))
                #on insert les transition de manière aléatoire
                states.insert(index,st)
                next_states.insert(index , st2)
                actions.insert(index , mask)
                rewards.insert(index , reward)

                #on insère les récompenses l'une après l'aute
                reward_mean.append(reward)


                if len(reward_mean) > 1000 :
                    del reward_mean[0]
                #on oblige la dataset à rester à 10000 éléments
                if len(states) > 10000 :
                    del states[0]
                    del next_states[0]
                    del actions[0]
                    del rewards[0]

                st = st2

                eps =  max([0.1,eps*0.99])

                if(i % 100 == 0):
                    print("\n\n")
                    print( "récompense moyenne : ", sum(reward_mean)/len(reward_mean) )
                    print("solde du compte :", env.account)

                    print("pourcentage d'évolution",(i/nb_episode)*100," %")

                if i % 2000 == 0:
                    self.train_model(states , actions , rewards , next_states)
