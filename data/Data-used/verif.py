import csv

d = open('./env.csv', 'r')

reader =   csv.reader(d)
a = []

for row in reader :
    a = row
    break
print(type(a[0]))

#tab = np.zeros((1,360))
