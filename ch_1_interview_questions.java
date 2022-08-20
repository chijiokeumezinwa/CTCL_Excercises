import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class CH1_Interview_Questions {
	// i want to use the implementations of methods 
	// as the solution to each question
	
	//Question 1 
	//Is Unique: Implement an algorithm to determine 
	//if a string has all unique characters. 
	//What if you cannot use additional data structures?
	
	public static boolean isUnique(String input) {
		//the last question is especially important about 
		//additional data structures
		//if we cant use additional data structures 
		//to see if a string has unique characters,
		//we have no choice but to only use the string 
		//as a means of answering our question
		
		// what we can do is to first change the string to
		//a char array. then we can sort it so if there is 
		//a repeating character we'll know right away vs 
		//having to examine the entire string against each 
		//character
		
		char[] charInput = input.toCharArray();
		
		//lets try merge sort
		charInput = mergeSort(charInput);
		
		//now lets try have a counter that sees if theres a repeat
		int c = 1;
		char prev = charInput[0];
		
		for(int i = 1; i < charInput.length; i++) {
			char current = charInput[i];
			
			if(current == prev) {
				c++;
				if(c > 1) {
					return false;
				}
			}
			else {
				prev = charInput[i];
				c=1;
			}
		}
	
		return true;
	}

	private static char[] mergeSort(char[] charInput) {
		// TODO Auto-generated method stub
		
		//Divide and recur
		int size = charInput.length;
		
		if(size < 2)
			return charInput;
		
		int mid = size /2;
		
		char [] left = Arrays.copyOfRange(charInput,0, mid);
		char [] right = Arrays.copyOfRange(charInput, mid, size);
		
		left = mergeSort(left);
		right = mergeSort(right);
		
		char[] result = new char[left.length + right.length];
		result = merge(left, right, result);
		return result;
	}

	private static char[] merge(char[] a, char[] b, char[] result) {
		// TODO Auto-generated method stub
		
		//inital indexes of a and b
		int i = 0;
		int j = 0;
		
		int k = 0;//initial index of result
		while (i < a.length && j < b.length) {
			int cmp = Character.compare(a[i], b[j]);
			
			if(cmp <= 0) {
				result[k] = a[i];
				i++;
			}
			else {
				result[k] = b[j];
				j++;
			}
			k++;
		}
		
		while(i < a.length) {
			result[k] = a[i];
			i++;
			k++;
		}
		
		while(j < b.length) {
			result[k] = b[j];
			j++;
			k++;
		}
		
		return result;
	}
	
	//Question 2:
	//Check Permutation: Given two strings,
	//write a method to decide if one is a 
	//permutation of the other.
	
	//so we can just simply sort the two strings
	//then they should have the same arrangement of characters
	
	public static boolean isPermutation(String a, String b) {
		//first lets get their char array equivalents
		
		char[] charA = a.toCharArray();
		char[] charB = b.toCharArray();
		
		//next lets sort the two char arrays
		charA = mergeSort(charA);
		charB = mergeSort(charB);
		
		//lets convert these two char arrays back to strings
		//if theyre equal return true otherwise return false
		
		String newA = String.valueOf(charA);
		String newB = String.valueOf(charB);
		
		if(newA.equals(newB))
			return true;
		return false;
	}
	
	//Question 3:
	//URLify: Write a method to replace all spaces in a string with '%20'. 
	//You may assume that the string has sufficient space at the end to hold 
	//the additional characters,and that you are given the "true" length of the string.
	//(Note: If implementing in Java,please use a character array so that you can perform 
	//this operation in place.)
	
	public static void URLify(String input) {
		//we can convert our input into a char array
		char[] charInput = input.toCharArray();
		
		//we can traverse through array, and add each character to 
		//a string. if we encounter a space we add the '%20' instead.
		String result = "";
		
		for(int i = 0; i< charInput.length; i++) {
			char e = charInput[i];
			if(e != ' ')
				result += e;
			else
				result += "%20";
		}
		
		System.out.println(result);
	}
	
	//Question 4:
	//Palindrome Permutation: Given a string, write a function to check if it is a permutation 
	//of a palin­drome. A palindrome is a word or phrase that is the same forwards and backwards. 
	//A permutation is a rearrangement of letters. The palindrome does not need to be limited to 
	//just dictionary words.
	public static boolean palindromePermutation(String input) {
		// first lets convert our input to a char array
		char[] charInput = input.toCharArray();

		
		//next we understand there is n! possible permutations of input,
		//with n being input.length
		
		//however, we are only interested in first
			//establishing if the input a palindrome
			//and outputting any other possible palindromes
		
		//to establish if it is a palindrome, we have to count the individual occurences
		//of each letter. after that, we know our sentence
		//to be a palindrome if the count of each letter is divisible by 2, followed by either no
		//more characters or a character count of 1.
		
		
		//how can we count characters?
		//we could do brute force technique of comparring each character by the rest of the array
		//but that could take O(N^2) time
		
		//we could sort our array so that we can then load each individual count of a character
		//onto a hashtable.
		
		//then afterward we can loop through all of our keys
		
		HashMap<Character, Integer> map = new HashMap<Character,Integer>();
		
		charInput = mergeSort(charInput);
		
		//lets try to check if a character is already in the hashmap
		for(char c : charInput) {
			if(map.containsKey(c)) {
				//increment value of count by 1
				int value = map.get(c)+1;
				map.put(c, value);
			}
			else
				map.put(c, 1);
		}
		
		
		//next let's browse through our hashtable.
		//lets first collect the keys.
		Set<Character> characterSet = map.keySet();
		//collect number of characters that have even occurences
		//(include spaces even if its not even)
		int evenCount = 0;
		//now lets get the number of unqiue characters.
		int numUniqueCharacters = characterSet.size();
		
		//as we loop through each character we want to count 
		//the number of characters with even occurences.
		//the only exception to this is also counting the space character whether 
		//its number of occurrences is even or odd.
		
		//afterward if this number is more than 1 less than the actual number of unqiue its false
		//otherwise its true
		
		for(char e: characterSet) {
			if(e == ' ')
				evenCount+=1;
			else {
				int numCount = map.get(e);
				if(numCount % 2 == 0)
					evenCount += 1;
			}
		}
		
		if( (numUniqueCharacters - evenCount) > 1)
			return false;
		return true;
	}
	
	//Problem 5
	//One Away: There are three types of edits that can be performed on strings: insert a character, 
	//remove a character, or replace a character. Given two strings, write a function to check if they 
	//are one edit (or zero edits) away.
	public static boolean oneAway(String a, String b) {
		//this problem seems complicated but it really isn't.
		//this problem is asking us to observe the number of occurences
		//for the unqiue characters of string a and b.
		//the edits such as inserting a character, removing a character, or replacing a character
		//all has to do with the observing the number of occurrences for said character.
		
		// the first step is to break the strings down to char arrays
		// then after sort them.
		
		//then map the occurence of each individual character to a hashtable
		
		// then try to pinpoint if the one change is possible 
		
		char [] charA = a.toCharArray();
		char [] charB = b.toCharArray();
		
		charA = mergeSort(charA);
		charB = mergeSort(charB);
		
		if(charA.length == charB.length)
			return oneAwayReplace(charA, charB);
		else if(charA.length < charB.length)
			return oneAwayInsert(charA, charB);
		else
			return oneAwayDelete(charA,charB);
	}
	private static boolean oneAwayDelete(char[] charA, char[] charB) {
		// TODO Auto-generated method stub
		//in this case, charA array has more elements than charB
		
		//if the length of charA is greater than one than charB, return false
		if(charA.length - charB.length > 1)
			return false;
		
		//otherwise charA should be 1 element larger in size than charB
		
		// i must compare elements of charB[0] - charB[charB.length-1]
		// and elements of charA[0] - charA[charB.length-1] to see if they match
		//if they match return true
		//otherwise return false
		
		int size = charB.length;
		
		for(int i = 0; i < size ; i++) {
			char ae = charA[i];
			char be = charB[i];
			
			if(ae != be )
				return false;
		}
		return true;
	}

	private static boolean oneAwayInsert(char[] charA, char[] charB) {
		// TODO Auto-generated method stub
		
		//in this case, charA array has less elements than charB
		
		//if the length of charB is greater than one than charA, return false
		
		if(charB.length - charA.length > 1)
			return false;
		
		//otherwise charB should be 1 element larger in size than charB
		
		// i must compare elements of charA[0] - charA[charA.length-1]
		// and elements of charB[0] - charB[charA.length-1] to see if they match
		//if they match return true
		//otherwise return false
		int size = charA.length;
		
		for(int i = 0; i < size; i++) {
			char ae = charA[i];
			char be = charB[i];
			
			if(ae != be )
				return false;
		}
		return true;
	}

	private static boolean oneAwayReplace(char[] charA, char[] charB) {
		// TODO Auto-generated method stub
		//i can compare the two arrays in same loop
		//i go by an index by index comparison of each character
		//if there are more than one replacements needed i need to 
		//return false, otherwise i return true
		
		int size = charA.length;
		int numDiff = 0;
		for(int i = 0; i < size ; i++) {
			char ae = charA[i];
			char be = charB[i];
			
			if(ae != be)
				numDiff++;
		}
		
		if(numDiff > 1)
			return false;
		return true;
	}
	
	//Problem 6:
	//String Compression: Implement a method to perform basic string compression 
	//using the counts of repeated characters. For example, the string aabcccccaaa 
	//would become a2blc5a3. If the "compressed" string would not become smaller than 
	//the original string, your method should return the original string. You can assume 
	//the string has only uppercase and lowercase letters (a - z).
	public static String strCompr(String input) {
		//what we can do is to convert this string to 
		// a char array
		
		char[] charInput = input.toCharArray();
		
		String finalInput= "";
		int count = 0;
		char e = charInput[0];
		//next we can traverse this array
		//increment counter if next element is equal to prev element
		//otherwise record current number and character, set counter to 1
		for(int i = 0 ; i < charInput.length ; i++) {
			if(e == charInput[i]) {
				count++;
			}
			else {
				finalInput += e;
				finalInput += count;
				count = 1;
			}
			e = charInput[i];
		}
		//add final versions of e and count
		finalInput += e;
		finalInput += count;
		
		//now compare the lengths of original string and compressed string
		int aLength = input.length();
		int bLength = finalInput.length();
		
		if(aLength < bLength)
			return input;
		return finalInput;
	}

	public static void main(String [] args) {
		@SuppressWarnings("resource")
		Scanner input = new Scanner(System.in);
		String a;
		/*
		//problem 1
		//first lets make sure we can reduce to lower
		//case our input for isunique
		System.out.println("We want to find a sentence that's"
				+ "unique.\nPlease enter a sentence and "
				+ "we'll tell you if its unique.");
		
		@SuppressWarnings("resource")
		Scanner input = new Scanner(System.in);
		String a = input.nextLine();
		a = a.toLowerCase();
		
		boolean result = isUnique(a);
		if(result)
			System.out.println("Sentence '" + a +"' is unique");
		else
			System.out.println("Sentence '" + a +"' is not unique");
		
		//problem 2
		//ispermutation
		System.out.println("Given two strings, find if they are permutations"
				+ " of each other or not");
		System.out.print("Please enter first string: ");
		String str1 = input.nextLine();
		str1.toLowerCase();
		System.out.println();
		System.out.print("Please enter second string: ");
		String str2 = input.nextLine();
		str2.toLowerCase();
		System.out.println();
		
		result = isPermutation(str1, str2);
		System.out.println(result);
		
		//problem 3
		//urlify
		System.out.println("We want to replace all spaces in a string with '%20'");
		System.out.print("Please enter your string: ");
		a = input.nextLine();
		
		URLify(a);
		 
		//problem 4
		//palindromePermutation
		System.out.println("Given a string, write a function to check if it is a permutation");
		System.out.print("String: ");
		a= input.nextLine();
		a = a.toLowerCase();
		boolean result = palindromePermutation(a);
		System.out.println(result);
		
		
		//problem 5
		//oneAway
		System.out.println("We want to compare two strings to see if theyre one edit away");
		System.out.println("Please enter the first string: ");
		String firstStr = input.nextLine();
		System.out.println();
		System.out.println("Please enter the second string: ");
		String secondStr = input.nextLine();
		System.out.println();
		
		result = oneAway(firstStr, secondStr);
		System.out.println(result);
		
		
		//problem 6
		//String Compression
		System.out.println("We want to perform basic string compression "
				+ "using the counts of repeated characters");
		System.out.println("Please enter string");
		String firstStr = input.nextLine();
		System.out.println();
		
		String o = strCompr(firstStr);
		
		System.out.println(o);
		*/
	}

}
