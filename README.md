# Table of Contents
1. [Dependencies and running instructions](README.md#dependencies-and-running-instructions)
2. [Approach](README.md#Approach)
3. [Data structures used](README.md#data-structures-used)
4. [Complexity analysis](README.md#complexity-analysis)

# Dependencies and running instructions
1. The source code is written in Java 10 and is called PharmacyCounting.java and resides the src folder.
3. Compilation of the source code generates two class files in the src folder: PharmacyCounting.class and PharmacyCounting$Drug.class.
4. The compilation instructions are specified in the run.sh file.

# Approach
1. A private Drug class was used for storing relevant information such as drug name, number of prescribers, total cost and a set of unique prescribers.  This class implements the Comparable interface and overrides the compareTo() method, which uthses the total_cost to sort Drug objects in descending order. 
2. A HashMap was created which mapped a drug key to the Drug object. The drug key was the drug name, which was trimmed of extraneous spaces and cast to lower case.
3. While reading the input file line-by-line, the HashMap was checked for the presence of the key generated from each drug name.
4. If the map did not contain the key, a new Drug object was created and the fields of the new drug object such as cost, number of prescribers and Set of prescribers was initialized.
5. If the map contained the key, the cost was updated and the Set was checked for the existence of the prescriber. If the prescriber did not exist, the prescriber was added to the Set and the count was updated.
6. After parsing the entire input file, the values of the HashMap were stored in an ArrayList of Drug objects, which was then sorted according to natural sorting order defined in the Drug class.
7. Finally, this sorted ArrayList was appended to the output file. The String representation of each Drug object in the ArrayList (defined in Drug class) was used for writing each line in the output file. 

# Data structures used
1. HashMap for mapping drug name (key) to Drug object.
2. HashSet for making sure that the same prescriber is not counted twice.
3. ArrayList was used for storing HashMap values for sorting.
4. Array of String was used for extracting the contents of each line that was read.

# Complexity analysis
1. Assume that the number of records in the input file is N, with M unique drugs. Assumption: length of each record is << number of records.
2. Parsing the file and storing each drug in the HashMap takes O(N) time as lookup and adding to Map and Set each take O(1) time.
3. Sorting the Drug objects by cost takes O(M.log(M)) time.
4. Writing the Drug objects to file takes O(M) time.
5. Hence, overall time complexity is Max(O(N), O(M.log(M))).
6. Assuming there are M unique drugs and P unique prescribers for each drug, the space complexity is O(M * P).
