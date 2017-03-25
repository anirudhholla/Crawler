import networkx as nx

import csv
G = nx.Graph()
with open('pagerankdata.csv',"r") as f:

    reader = csv.reader(f)


    for row in reader:
        for col in range(1,len(row)-1):
            G.add_edge(row[0],row[col])

    #print(G.nodes())
    '''
    for i in G.edges():
        print(i)
    '''
    pr = nx.pagerank(G,alpha=0.9)
    target = open('external_pageRankFile.txt',"w")


    for items in pr.keys():
        target.write("/home/anirudh/Documents/solr-5.3.1/downloads/"+ str(items) + "=" + str(pr[items]) + "\n")
    target.close()

