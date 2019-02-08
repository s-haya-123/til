# -*- coding: utf-8 -*-
N = int(input())
a = list(map(int, input().split()))


sorted_a = sorted(a,reverse=True)

bob=0
alice=0

for i in range(0,N):
    if i%2==0:
        alice+=sorted_a[i]
    else:
        bob+=sorted_a[i]

print(alice-bob)