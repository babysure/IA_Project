import csv

f = open('./test.csv','r').readlines()
nb = 1
reader = csv.reader(f)

line= next(reader)
print("la ligne est :" ,line )
line2 = next(reader)
print("la ligne est :" ,line2 )
