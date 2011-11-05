from itertools import *

letters = set('ABCDEFGHIJKLMNOPQR')

for A_hand in combinations(letters, 5):
    for B_hand in combinations(set(letters)-set(A_hand), 5):
        gang = set(letters) - set(A_hand) - set(B_hand)
        
