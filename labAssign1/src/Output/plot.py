import matplotlib
import sys
matplotlib.use('SVG')
import matplotlib.pyplot as pyplot

def readData(filename):
	dataR = open(filename,'r')
	datax = []
	datay = []
	read = dataR.readline()
	while(read):
		points = read.split(" ");
		print(points)
		datax.append(points[0])
		datay.append(points[1])
		read = dataR.readline()
	return {'x': datax,'y' :datay}

args_list = sys.argv
args_list.pop(0)
title = args_list.pop(0)
xlabel = args_list.pop(0)
ylabel = args_list.pop(0)
outputname = args_list.pop(0)
for filename in args_list:
	print(filename)	
	data = readData(filename)
	pyplot.plot(data['x'],data['y'],linestyle='-', marker='o',label=filename)
	pyplot.legend(fancybox=True, shadow=True, ncol=2)
	
pyplot.title(title)
pyplot.xlabel(xlabel)
pyplot.ylabel(ylabel)
pyplot.savefig(outputname +".png")
print('Graph done')
