#
# This is the default configuration for ArrayScript V2.0
#
# Recommended to use as it sets up the environment.
# This is not required, all commands can be executed just fine
# without the use or existence of this file.
#
# NOTE: It saves various attributes about the environment that
# may be helpful, along with a version code. If you don't want
# these, or they are not useful to you, DON'T RUN THE FILE.
#
# Editable to customize the environment.
#
# - Luis Hoderlein
#

# Create the array and saves the size
rz(1048576)

# Allow for the use of rd(0) to get array size
wr(0, /m)

# VERSION is in index 1-8:
us(1, 2.01.24)

# AUTHOR is in index 9-31:
us(9, Luis/_Hoderlein)

# CLEAR everything else
cl(64, sb(rd(0), 64))

# Print the ArrayScript information header
pr(==================================/n)
pr(as(as(Created/_a/_machine/_of/_size:/_, rd(0)), /n))
pr(as(as(ArrayScript/_Version:/_,ts(1, 7)), /n))
pr(as(as(Author:/_, ts(9, 23)), /n))
pr(==================================/n)
