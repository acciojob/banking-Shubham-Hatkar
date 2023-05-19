package com.driver;

import java.util.Arrays;

public class CurrentAccount extends BankAccount{
    String tradeLicenseId; //consists of Uppercase English characters only

    public CurrentAccount(String name, double balance, String tradeLicenseId) throws Exception
    {
        // minimum balance is 5000 by default. If balance is less than 5000, throw "Insufficient Balance" exception
        super(name, balance, 5000);
        this.tradeLicenseId = tradeLicenseId;
        if(balance < 5000)
            throw new Exception("Insufficient Balance");
    }

    public void validateLicenseId() throws Exception {
        // A trade license Id is said to be valid if no two consecutive characters are same
        // If the license Id is valid, do nothing
        // If the characters of the license Id can be rearranged to create any valid license Id
        // If it is not possible, throw "Valid License can not be generated" Exception
        if(isTwoConsecutive(tradeLicenseId))
        {
            String reArrange = arrangeId(tradeLicenseId);
            if(reArrange.equals("-1"))
            {
                throw new Exception("Valid License can not be generated");
            }
            else this.tradeLicenseId = reArrange;
        }
    }

    private String arrangeId(String tradeLicenseId)
    {
        int n = tradeLicenseId.length();

        int freq[] = new int[26];
        for(int i = 0; i < n; i++)
        {
            char ch = tradeLicenseId.charAt(i);
            freq[ch - 'A']++; // capital characters
        }

        char charWithMaxCount = getMaxCountChar(freq);
        int maxCount = freq[charWithMaxCount - 'A'];

        if(maxCount > (n + 1) / 2)
        {
            return "";
        }

        char arr[] = new char[n];
        Arrays.fill(arr,'1');
        int idx = 0;
        while(maxCount > 0)
        {
            arr[idx] = charWithMaxCount;
            idx += 2;
            maxCount--;
        }
        freq[charWithMaxCount] = 0;
        for(int i = 0; i < 26; i++)
        {
            while(freq[i] > 0)
            {
                idx = (idx >= n) ? 1 : idx;
                arr[i] = (char)('A' + i);
                idx += 2;
                freq[i]--;
            }
        }
        return new String(arr);

    }

    // find char with max freq.
    private char getMaxCountChar(int[] freq)
    {
        int max = 0;
        char ch = 'A';
        for(int i = 0; i < 26; i++)
        {
            if(max > freq[i])
            {
                max = freq[i];
                ch = (char)('A' + i);
            }
        }
        return ch;
    }


    private boolean isTwoConsecutive(String tradeLicenseId)
    {
        int n = tradeLicenseId.length();
        for(int i = 0; i < n - 1; i++)
        {
            if(tradeLicenseId.charAt(i) != tradeLicenseId.charAt(i+1)) return true;
        }
        return false;
    }

}
