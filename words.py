import bs4
import sys
import re
from os import listdir
from os.path import isfile, join
onlyfiles = [f for f in listdir("/home/anirudh/Documents/data/html") if isfile(join("/home/anirudh/Documents/data/html", f))]
print ((onlyfiles))

def main():
    h1_set = set()
    g= open("big.txt","w")

    for of in onlyfiles:
        filename = "/home/anirudh/Documents/data/html/" + str(of)
        fd = open (filename, "r")
        whole_file = ""
        for line in fd:
            whole_file += line
        cleanr =re.compile('<.*?>')

        #x = re.compile(r'<[^<]*?/?>')

        tree = bs4.BeautifulSoup(whole_file, "lxml")
        h1 = tree.find_all ("h1")
        h2 = tree.find_all ("h2")
        h3 = tree.find_all ("h3")
        h4 = tree.find_all ("h4")
        h5 = tree.find_all ("h5")
        h6 = tree.find_all ("h6")
        p = tree.find_all ("p")
        title = tree.find_all ("title")

        cleantext1 = str(re.sub(cleanr,'', str(h1)))
        cleantext1 = re.sub(r'[^\w]', ' ', cleantext1)

        cleantext2 = str(re.sub(cleanr,'', str(h2)))
        cleantext2 = re.sub(r'[^\w]', ' ', cleantext2)

        cleantext3 = str(re.sub(cleanr,'', str(h3)))
        cleantext3 = re.sub(r'[^\w]', ' ', cleantext3)

        cleantext4 = str(re.sub(cleanr,'', str(h4)))
        cleantext4 = re.sub(r'[^\w]', ' ', cleantext4)

        cleantext5 = str(re.sub(cleanr,'', str(h5)))
        cleantext5 = re.sub(r'[^\w]', ' ', cleantext5)

        cleantext6 = str(re.sub(cleanr,'', str(h6)))
        cleantext6 = re.sub(r'[^\w]', ' ', cleantext6)

        cleantextp = str(re.sub(cleanr,'', str(p)))
        cleantextp = re.sub(r'[^\w]', ' ', cleantextp)

        cleantext_t = str(re.sub(cleanr,'', str(title)))
        cleantext_t = re.sub(r'[^\w]', ' ', cleantext_t)
        #print (cleantextp)
        words_h1 = cleantext1.split()
        words_h2 = cleantext2.split()
        words_h3 = cleantext3.split()
        words_h4 = cleantext4.split()
        words_h5 = cleantext5.split()
        words_h6 = cleantext6.split()
        words_title = cleantext_t.split()

        words_p = cleantextp.split()
        #print(words_p)

        #print ("H1 = ", h1)

        for value in words_h1:
            #print ("H1 Value=", value)
            for value1 in value:
                #print ("Value in H1 =", value1)
                temp1 = value1.split()
                for v in temp1:
                    if len(v)>3:
                        h1_set.add(v)

        for value in words_h2:
            for value1 in value:
                temp1 = value1.split()
                for v in temp1:
                    if len(v)>3:
                        h1_set.add(v)


        #print (cleantext)

        for value in words_h3:
            #print("Origninal H3=",value)
            for value1 in value:
                #print ("Value in H3 =", value1)
                temp1 = value1.split()
                for v in temp1:
                    if len(v)>3:
                        h1_set.add(v)

        for value in words_h4:
            for value1 in value:
                temp1 = value1.split()
                for v in temp1:
                    if len(v)>3:
                        h1_set.add(v)

        for value in words_h5:
            for value1 in value:
                temp1 = value1.split()
                for v in temp1:
                    if len(v)>3:
                        h1_set.add(v)

        for value in words_h6:
            for value1 in value:
                temp1 = value1.split()
                for v in temp1:
                    if len(v)>3:
                        h1_set.add(v)
        '''
        #cleantext = str(re.sub(cleanr,'', str(p)))
        #cleantext = re.sub(r'[^\w]', ' ', cleantext)
        #print (cleantext)
        '''
        for value in words_p:
            #print("Value",value)
            temp1 = value.split()
            for v in temp1:
                if len(v)>3:
                    h1_set.add(v)

        for value in words_title:
            temp1 = value.split()
            for v in temp1:
                if len(v)>3:
                    h1_set.add(v)

        #print (len(h1_set))
    g.write(str(h1_set))

if __name__ == "__main__":
    main()