# -*- coding: utf-8 -*-
K, A, B = map(int, input().split())

efficiency = B - A
change_cnt = K - A + 1
if efficiency <= 2 or change_cnt < 2:
    print(1 + K)
else:
    if change_cnt % 2 ==0:
        print( int(change_cnt/2)*efficiency+A )
    else:
        print( int(change_cnt/2)*efficiency+A+1)
