# -*- coding: utf-8 -*-
N,M = map(int, input().split())

food = [0] * M

for i in range(0,N):
    Ks =  list(map(int, input().split()))
    K = Ks[0]
    for j in range(1,K+1):
        food[Ks[j]-1] += 1

cnt=0
for i in range(0,M):
    if food[i] == N:
        cnt+=1

print(cnt)