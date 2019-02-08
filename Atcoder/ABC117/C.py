# -*- coding: utf-8 -*-
import sys

N, M = map(int, input().split())
X = map(int, input().split())

if N > M:
    print(0)
    sys.exit()
sorted_x = sorted(list(X))

sub_X = [abs(sorted_x[i+1] - sorted_x[i]) for i in range(0,M-1)]

sorted_sub_x = sorted(sub_X,reverse=True)

print( sorted_x[M-1] -sorted_x[0] - sum([ sorted_sub_x[i] for i in range(0,N-1)]) )
