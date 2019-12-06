from env import *
from DQN import *

training = VirstualEnv("./data/Data-used/env.csv")
#il y a 362 variable d'environement


IA = DQNAgent(362)
IA.training(env = training , nb_episode = 100000 )
#marketEnv.DoAction(
