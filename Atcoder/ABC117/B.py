# -*- coding: utf-8 -*-
N = int(input())
L = map(int, input().split())
sumL = 0
maxL = -1
for Li in L:
    sumL += Li
    if Li > maxL:
        maxL = Li
    
sumL -= maxL

if maxL < sumL:
    print("Yes")
else:
    print("No")