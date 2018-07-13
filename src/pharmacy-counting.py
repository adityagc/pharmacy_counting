#This code contains my submission for the Insight data engineering fellowship program. 
#Consider dictreader class
import csv
import json
#import Dialect
with open('../input/itcont.txt', 'r') as infile:
    readobj = csv.reader(infile, delimiter=',', quotechar='|')
    #map drug name to a tuple of frequency
    
    #readobj
    drugmap = {}
    for line in readobj:
        if (readobj.line_num != 1):
            if (line[3] == None):
                continue
            else:
                drugname = line[3]
                if (line[4] == None):
                    drugcost = 0
                else:
                    drugcost = float(line[4].strip())
                if (drugmap.get(drugname) == None):
                    druginfo =  [1, drugcost]
                    drugmap[drugname] = druginfo
                else:
                    druginfo = drugmap.get(drugname)
                    druginfo[0] = druginfo[0] + 1
                    druginfo[1] = (druginfo[1]) + drugcost
                    drugmap[drugname] = druginfo
    sorter = lambda key : drugmap.get(key)[1]
    with open('../output/top_cost_drug.txt', 'w') as outfile:
        print ("drug_name,num_prescriber,total_cost", file=outfile)
        for k in sorted(drugmap, key=sorter, reverse=True):
            print (k, drugmap[k][0], int(drugmap[k][1]), file=outfile, sep=",")
    
        
#    with open('output.txt', 'w') as outfile:
#        dialect = Dialect.delimiter(".")
#        writer = csv.writer(outfile, dialect=dialect)
#        writer.writerows(s)
#        for k, v in s:
#            print(k ',' v[0], ',' ,v[1], file=outfile)
