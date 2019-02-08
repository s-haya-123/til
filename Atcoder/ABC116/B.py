# -*- coding: utf-8 -*-
s = int(input())

dp = [s]

i=2
a_1=s
while(True):
    if a_1 % 2==0:
        index = a_1 /2
    else:
        index = 3 * a_1 + 1
    if int(index) in dp:
        break
    else:
        dp.append(int(index))
        a_1 = int(index)
        i+=1

print(i)