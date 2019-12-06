import csv

f5m = open('./EURUSD5M.csv', 'r')
f1h = open('./EURUSD1H.csv', 'r')
f1j = open('EURUSD1J.csv', 'r')

def initalisation(nom_dossier):
    dir_temp = open('./'+ nom_dossier,'r')
    reader_temp = list(csv.reader(dir_temp))
    liste_temp = []

    #on remplie la liste listej1 avec les 20 premiers éléments de read_1j
    for i in range(20):
            liste_temp.append(reader_temp[i])
    return liste_temp , reader_temp

liste5m , reader5m = initalisation('EURUSD5M.csv')
liste1h , reader1h = initalisation('EURUSD1H.csv')
liste1j , reader1j = initalisation('EURUSD1J.csv')

def tranformListe(liste):
    newliste = []
    for i in range(len(liste)):
        for k in range(len(liste[0])):
            if k > 1 :
                newliste.append(liste[i][k])
    return newliste

def ajouteDansListe(liste, element):
    #les élément avec les indices les plus petits sont les plus anciens
    del liste[0]
    liste.append(element)
    #print("\n\n après == \n",liste)
    return liste

def changeListe(reader_file , list):

    change_ligne_proch = False
    count = 0

    for line in reader_file :
        if change_ligne_proch == True :
            count+=1


        if count == 20 :
            # on change la liste
            #les élément avec les indices les plus petits sont les plus anciens
            del list[0]
            list.append(line)
            return list

        if line[0] == list[0][0] and line[1] == list[0][1] :
            change_ligne_proch = True


#print("avant liste5m =  ",liste5m)
#liste5m = choisitElement(reader5m , liste5m)
#print("\n\n\n après listej1 =  ",liste5m)

def makeEnvVector(liste_J, liste_H , liste_M):

    liste_H_temp = tranformListe(liste_H)

    liste_J_temp = tranformListe(liste_J)

    liste_M_temp = tranformListe(liste_M)

    return liste_M_temp + liste_H_temp + liste_J_temp


dataset = open('dataset.csv' , 'w' , newline='')
dataset = csv.writer(dataset)
count = 0
#liste_env = makeEnvVector(liste1j,liste1h,liste5m)
#print(liste_env)
#count = nb de ligne de la dataset = nb de ligne de EURUSD5M.csv
for bidon in reader5m :

    count+= 1
    liste_env = makeEnvVector(liste1j,liste1h,liste5m)

    #ici écrire une nouvelle ligne dans la liste des features de la dataset
    dataset.writerow(liste_env)
    print(' process :  ',int((count*100)/1188373),' % \n nb ligne :  ',count,'\n\n')

    #on change de liste
    liste5m = changeListe(reader5m , liste5m)

    # il y  a 12 fois 5min dans une heure
    if count % 12 == 0 :
        liste1h = changeListe(reader1h , liste1h)


    #il y a 288 fois 5min dans une jounée
    if count % 288 == 0 :
        liste1j = changeListe(reader1j , liste1j)
