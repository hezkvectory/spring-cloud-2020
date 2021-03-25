package com.fishercoder.solutions;

import static java.lang.Math.max;
import static java.lang.Math.min;

/**
 * 4. Median of Two Sorted Arrays
 * <p>
 * There are two sorted arrays nums1 and nums2 of size m and n respectively.
 * <p>
 * Find the median of the two sorted arrays. The overall run time complexity should be O(log (m+n)).
 * <p>
 * Example 1:
 * nums1 = [1, 3]
 * nums2 = [2]
 * The median is 2.0
 * <p>
 * Example 2:
 * nums1 = [1, 2]
 * nums2 = [3, 4]
 * The median is (2 + 3)/2 = 2.5
 */
public class _4 {

    public static class Solution1 {
        /**
         * credit: https://discuss.leetcode.com/topic/28602/concise-java-solution-based-on-binary-search
         * <p>
         * The key point of this problem is to ignore half part of A and B each step recursively by comparing the median of remaining A and B:
         * <p>
         * if (aMid < bMid) Keep [aRight + bLeft]
         * else Keep [bRight + aLeft]
         * <p>
         * As the following: time=O(log(m + n))
         */
        public double findMedianSortedArrays(int[] A, int[] B) {
            int m = A.length;
            int n = B.length;
            int l = (m + n + 1) / 2;
            int r = (m + n + 2) / 2;
            return (getkth(A, 0, B, 0, l) + getkth(A, 0, B, 0, r)) / 2.0;
        }

        public double getkth(int[] A, int aStart, int[] B, int bStart, int k) {
            if (aStart > A.length - 1) {
                return B[bStart + k - 1];
            }
            if (bStart > B.length - 1) {
                return A[aStart + k - 1];
            }
            if (k == 1) {
                return Math.min(A[aStart], B[bStart]);
            }

            int aMid = Integer.MAX_VALUE;
            int bMid = Integer.MAX_VALUE;
            if (aStart + k / 2 - 1 < A.length) {
                aMid = A[aStart + k / 2 - 1];
            }
            if (bStart + k / 2 - 1 < B.length) {
                bMid = B[bStart + k / 2 - 1];
            }

            if (aMid < bMid) {
                return getkth(A, aStart + k / 2, B, bStart, k - k / 2);// Check: aRight + bLeft
            } else {
                return getkth(A, aStart, B, bStart + k / 2, k - k / 2);// Check: bRight + aLeft
            }
        }
    }

    public static class Solution2 {
        /**
         * Reference: https://leetcode.com/discuss/28843/my-accepted-java-solution:
         * Basic Idea is very similar to K-selection. it's easier to understand if you imagine this to be chopping off the last K elements from a total of len(A) + len(B) elements,
         * where K = (len(A) + len(B))/2.
         * we want to remove K, but each time we can remove only at most K/2 elements,
         * because we can only be sure that these elements are not within the first (len(A) + len(B)) -K elements.
         */
        public double findMedianSortedArrays(int[] nums1, int[] nums2) {
            int K = nums1.length + nums2.length;
            if (K % 2 == 0) {
                return (findMedianSortedArrays(nums1, nums2, (K - K / 2)) + findMedianSortedArrays(nums1, nums2, (K - (K / 2 + 1)))) / 2;
            } else {
                return findMedianSortedArrays(nums1, nums2, K - (K / 2 + 1));
            }
        }

        // k is the number of elements to REMOVE, or "Chop off"
        public double findMedianSortedArrays(int[] A, int[] B, int K) {

            int lowA = 0;
            int lowB = 0;
            int highA = A.length;
            int highB = B.length;
            int midA;
            int midB;
            while (K > 0 && highA > 0 && highB > 0) {
                int chopA = max(1, min(K / 2, (highA) / 2));
                int chopB = max(1, min(K / 2, (highB) / 2));

                midA = highA - chopA;
                midB = highB - chopB;
                if (A[midA] < B[midB]) { // here A[0 .. midA] < B[midB], and we know that B[0 .. midB-1] < B[midB], so B[midB..highB] can not possibly be within the first (len(A) + len(B) - K) elements, and can be safely removed.
                    highB = midB;
                    K = K - chopB;
                } else {
                    highA = midA;
                    K = K - chopA;
                }
            }

            if (highA == 0 && highB == 0) {
                return 0;
            }
            if (highA == 0) {
                return B[highB - 1 - K];
            }
            if (highB == 0) {
                return A[highA - 1 - K];
            }
            return max(A[highA - 1], B[highB - 1]);
        }
    }

    public static class Solution3 {
        public double findMedianSortedArrays(int[] A, int[] B) {
            int m = A.length;
            int n = B.length;
            if (m > n) { // to ensure m<=n
                int[] temp = A;
                A = B;
                B = temp;
                int tmp = m;
                m = n;
                n = tmp;
            }
            int iMin = 0, iMax = m, halfLen = (m + n + 1) / 2;
            while (iMin <= iMax) {
                int i = (iMin + iMax) / 2;
                int j = halfLen - i;
                if (i < iMax && B[j - 1] > A[i]) {
                    iMin = i + 1; // i is too small
                } else if (i > iMin && A[i - 1] > B[j]) {
                    iMax = i - 1; // i is too big
                } else { // i is perfect
                    int maxLeft = 0;
                    if (i == 0) {
                        maxLeft = B[j - 1];
                    } else if (j == 0) {
                        maxLeft = A[i - 1];
                    } else {
                        maxLeft = Math.max(A[i - 1], B[j - 1]);
                    }
                    if ((m + n) % 2 == 1) {
                        return maxLeft;
                    }

                    int minRight = 0;
                    if (i == m) {
                        minRight = B[j];
                    } else if (j == n) {
                        minRight = A[i];
                    } else {
                        minRight = Math.min(B[j], A[i]);
                    }

                    return (maxLeft + minRight) / 2.0;
                }
            }
            return 0.0;
        }
    }

    public static class Solution4 {
        public double findMedianSortedArrays(int[] nums1, int[] nums2) {
            int n1 = nums1.length;
            int n2 = nums2.length;
            if (n1 > n2)
                return findMedianSortedArrays(nums2, nums1);
            int k = (n1 + n2 + 1) / 2; //10
            int left = 0;
            int right = n1;//6
            while (left < right) {
                int m1 = left + (right - left) / 2;
                int m2 = k - m1;
                if (nums1[m1] < nums2[m2 - 1])
                    left = m1 + 1;
                else
                    right = m1;
            }
            int m1 = left;
            int m2 = k - left;
            int c1 = Math.max(m1 <= 0 ? Integer.MIN_VALUE : nums1[m1 - 1],
                    m2 <= 0 ? Integer.MIN_VALUE : nums2[m2 - 1]);
            if ((n1 + n2) % 2 == 1)
                return c1;
            int c2 = Math.min(m1 >= n1 ? Integer.MAX_VALUE : nums1[m1],
                    m2 >= n2 ? Integer.MAX_VALUE : nums2[m2]);
            return (c1 + c2) * 0.5;

        }
    }

    public static class Solution {

        public double findMedianSortedArrays(int[] nums1, int[] nums2) {
            int len1 = nums1.length, len2 = nums2.length;
            int totalLen = len1 + len2;
            if (totalLen % 2 == 0) {
                return (find(nums1, nums2, totalLen / 2) + find(nums1, nums2, totalLen / 2 + 1)) / 2.0;
            } else {
                return find(nums1, nums2, totalLen / 2 + 1);
            }
        }

        public int find(int[] nums1, int[] nums2, int k) {
            int len1 = nums1.length, len2 = nums2.length;
            int index1 = 0;
            int index2 = 0;

            while (true) {
                if (index1 == len1) {
                    return nums2[k + index2 - 1];
                }
                if (index2 == len2) {
                    return nums1[k + index1 - 1];
                }
                if (1 == k) {
                    return Math.min(nums1[index1], nums2[index2]);
                }

                int half = k / 2;
                int newIndex1 = Math.min(index1 + half, len1) - 1;
                int newIndex2 = Math.min(index2 + half, len2) - 1;
                if (nums1[newIndex1] < nums2[newIndex2]) {
                    k -= (newIndex1 - index1 + 1);
                    index1 = newIndex1 + 1;
                } else {
                    k -= (newIndex2 - index2 + 1);
                    index2 = newIndex2 + 1;
                }
            }
        }

    }

    public static void main(String[] args) {
        int[] nums1 = {-1, 1};//3, 5, 7, 9
        int[] nums2 = {2, 4, 6, 8, 10, 12, 14, 16, 20, 22, 23, 24, 25};

        // -1, 1,2,3, 4,5,6,7, 8,9,10, 12,14,16,20, 22,23,24,25
        Solution solution4 = new Solution();
        double num = solution4.findMedianSortedArrays(nums1, nums2);
        System.out.println(num);
    }
}
