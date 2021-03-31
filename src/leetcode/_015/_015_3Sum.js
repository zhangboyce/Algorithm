/**
 Given an array nums of n integers, are there elements a, b, c in nums such that a + b + c = 0? Find all unique triplets in the array which gives the sum of zero.

 Note:

 The solution set must not contain duplicate triplets.

 Example:

 Given array nums = [-1, 0, 1, 2, -1, -4],

 A solution set is:
 [
 [-1, 0, 1],
 [-1, -1, 2]
 ]
 */

var threeSum = function(nums) {
    if (!nums || nums.length < 3) return [];
    nums = nums.sort((a,b) => a - b);

    if (nums[0] === 0) {
        if ( nums[1] === 0 && nums[2] === 0) {
            return [[0,0,0]]
        } else {
            return [];
        }
    }

    let l = nums.length;
    let result = [];
    for (let i=0; nums[i]<=0; i++) {
        if (i !==0 && nums[i] === nums[i-1] ) continue;
        for (let j=l-1; nums[j]>=0; j--) {
            if (i !==l-1 && nums[j] === nums[j+1] ) continue;
            let sum = nums[i] + nums[j];
            if (sum <= 0 && -sum<=nums[j]) {
                for (let n=j-1; nums[n]>=0 && n > i; n--) {
                    if (nums[n] === -sum) {
                        result.push([nums[i], nums[n], nums[j]]);
                        break;
                    }
                }
            }
            if (sum > 0 && -sum >=nums[i]) {
                for (let n=i+1; nums[n]<0; n++) {
                    if (nums[n] === -sum) {
                        result.push([nums[i], nums[n], nums[j]]);
                        break;
                    }
                }
            }
        }
    }

    return result;
};
