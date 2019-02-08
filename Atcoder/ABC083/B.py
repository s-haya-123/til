# -*- coding: utf-8 -*-
import functools

N,A,B = map(int, input().split())

sum = 0
for i in range(0,N+1):
    sum_i = int(functools.reduce(lambda sum,s: int(sum) + int(s),str(i) ))
    if sum_i >= A and sum_i <= B:
        sum += i

print(sum)