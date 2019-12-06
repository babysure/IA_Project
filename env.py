import csv
import numpy as np

class VirstualEnv():
    """docstring for env """

    def __init__(self, filename):
        file = open(filename , 'r')
        self.market = csv.reader(file)
        self.marketPosition = [0 , 0 ]
        self.curtPrceRank = 117

        self.account  = 0

    def doAction(self , action , market_state):

        #action 0 =  ne rien faire  , action 1 = acheter  , action 2 = vendre
        #touts les positions qui sont prises sont à 10 $ le pip

        #si on est sur une position acheteuse
        if self.marketPosition[0] == 1 :
            self.account = self.account + 500*200*( market_state[self.curtPrceRank] - self.marketPosition[1]  )

        #si on est sur une position vendeuse
        if self.marketPosition[0] == -1 :
            self.account = self.account + 500*200*( self.marketPosition[1] -market_state[self.curtPrceRank] )

        if action == 1 and  self.marketPosition[0] == 0 :
            #on prend une position acheteuse (1) au prix 'actuel' (market_state[117])
            self.marketPosition = [1 , market_state[self.curtPrceRank] ]


        if action == 2 and self.marketPosition[0] == 0 :
            #on prend une position vendeuse (1) au prix 'actuel' (market_state[117])
            self.marketPosition = [-1 , market_state[self.curtPrceRank] ]


        if action == 1  and self.marketPosition[0] == 2 :
            #on cloture une position vendeuse (2) au prix 'actuel' (market_state[117])
            self.marketPosition = [0 , 0]

        if action == 2  and self.marketPosition[0] == 1 :
            #on cloture une position acheteuse (2) au prix 'actuel' (market_state[117])
            self.marketPosition = [0 , 0]


    def CalcReward(self , market_state):

        reward = 0
        if self.marketPosition[0] == 0 :
            #si aucune position à été prise récompense  = 0
            reward = 0

        if self.marketPosition[0] == 1 :
            #si il y a eu prise de position à l'achat
            # récompense = profit en pourcentage
            reward = (( market_state[self.curtPrceRank] - self.marketPosition[1] )/self.marketPosition[1]) * 100


        if self.marketPosition[0] == -1 :
            #si il y a eu vente à découvert
            # récompense = porfit en pourcentage

            reward = ((self.marketPosition[1] - market_state[self.curtPrceRank] )/self.marketPosition[1]) * 100


        return reward


    def make_feature_vector(self, marketState) :
        #la première couche fait 363 nerone : 360 pour l'état du marché ,
        # 1 pour la positionActuel , 1 pour le prix d'ouverture de la position
        #et 1 pour la récompense
        temp = np.zeros((1,len(marketState)+len(self.marketPosition)))
        for k in range(len(marketState)):
            temp[0][k] = float(marketState[k])

        temp[0][-2] = self.marketPosition[0]
        temp[0][-1] = self.marketPosition[1]
        return temp
