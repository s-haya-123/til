# -*- coding: utf-8 -*-
N = int(input())

A = list(map(int, input().split()))

i=0
gcd=2
while(True):
    flag=False
    for Ai in A:
        if Ai % gcd != 0:
            flag=True
            break
    if flag:
        break
    else:
        gcd *= 2
        i+=1

print(i)