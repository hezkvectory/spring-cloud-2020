package com.fishercoder.solutions.mianshi;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class M_3 {

    public static void main(String[] args) {
        int[] nums = {9, 0, 1, 8, 7, 6, 5, 3, 4, 2, 1, 0};
        Solution solution = new Solution();
        int[] result = solution.smallestK(nums, 4);
        List<Integer> list = Arrays.stream(result).boxed().collect(Collectors.toList());
        System.out.println(list);
    }

    public static class Solution {

        private int[] smallestK(int[] nums, int k) {
            if (k <= 0) {
                return new int[0];
            }
            if (nums.length <= k) {
                return nums;
            }
            int left = 0, right = nums.length - 1;
            while (true) {
                int povit = partition(nums, left, right);
                if (povit == k) {
                    break;
                } else if (k < povit) {
                    right = povit - 1;
                } else {
                    left = povit + 1;
                }
            }
            int[] temp = new int[k];
            System.arraycopy(nums, 0, temp, 0, k);
            return temp;
        }

        private int partition(int[] nums, int left, int right) {
            int pivot = nums[left];
            int j = left;
            for (int i = left + 1; i <= right; i++) {
                // 小于 pivot 的元素都被交换到前面
                if (nums[i] < pivot) {
                    j++;
                    swap(nums, i, j);
                }
            }
            // 在之前遍历的过程中，满足 [left + 1, j] < pivot，并且 (j, i] >= pivot
            swap(nums, left, j);
            // 交换以后 [left, j - 1] < pivot, nums[j] = pivot, [j + 1, right] >= pivot
            return j;
        }

        private int partition1(int[] nums, int left, int right) {
            int pivot = left;
            int _left = left + 1;
            int _right = right;
            while (true) {
                while (_left <= right && nums[_left] < nums[pivot]) {
                    _left++;
                }
                while (_right >= left && nums[_right] > nums[pivot]) {
                    _right--;
                }
                if (_right < _left) {
                    break;
                }
                swap(nums, _left, _right);
                _left++;
                _right--;
            }
            swap(nums, pivot, _right);
            return _right;
        }

        private void swap(int[] nums, int i, int j) {
            int temp = nums[i];
            nums[i] = nums[j];
            nums[j] = temp;
        }
    }
}
