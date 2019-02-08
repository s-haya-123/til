# -*- coding: utf-8 -*-
import sys
N,Y=map(int, input().split())

num_ten_thouthand = int(Y / 10000)

# num_five_thouthand = int((N % 10000) / 5000)

for i_ten_thouthand in range(0,num_ten_thouthand+1):
    for i_five_thouthand in range(0,N - i_ten_thouthand +1):
        i_thouthand = N  - i_ten_thouthand - i_five_thouthand
        if i_thouthand * 1000 + i_five_thouthand * 5000 + i_ten_thouthand * 10000 == Y:
            print("{} {} {}".format(i_ten_thouthand,i_five_thouthand,i_thouthand))
            sys.exit()

print("-1 -1 -1")

