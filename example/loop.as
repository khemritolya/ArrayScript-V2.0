im(example/dconf)

# index
wr(101, 0)

# max
wr(102, 4)

lb()

# Print out the index
pr(as(rd(101), /n))

# Increment the index
wr(101, ad(rd(101), 1))

# Loop back
gt(dv(rd(101), rd(102)))

lb()
pr(Done!)
