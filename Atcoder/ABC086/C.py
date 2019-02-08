# -*- coding: utf-8 -*-
import sys
N = int(input())

origin_x = 0
origin_y = 0
origin_t = 0

for i in range(0,N):
    t,x,y = map(int, input().split())
    distance_x = abs(x - origin_x)
    distance_y = abs(y - origin_y)
    sub_t = t - origin_t
    if distance_x + distance_y > sub_t:
        print('No')
        sys.exit()
    elif (sub_t - distance_x - distance_y) % 2 == 0:
        origin_x = x
        origin_y = y
        origin_t = t
    else:
        print('No')
        sys.exit()

print('Yes')