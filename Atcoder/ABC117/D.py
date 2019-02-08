# -*- coding: utf-8 -*-

N,K = map(int, input().split())
A = list(map(int, input().split()))
def f(x,A):
    return sum([Ai^x  for Ai in A])

max = 0

for x in range(0,K+1):
    value = f(x,list(A))
    if value > max:
        max = value
    
print(max)