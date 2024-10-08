package trees.BST;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * https://leetcode.com/problems/construct-binary-search-tree-from-preorder-traversal/
 * Return the root node of a binary search tree that matches the given preorder traversal.
 * <p>
 * (Recall that a binary search tree is a binary tree where for every node,
 * any descendant of node.left has a value < node.val,
 * and any descendant of node.right has a value > node.val.
 * Also recall that a preorder traversal displays the value of the node first,
 * then traverses node.left, then traverses node.right.)
 */
public class ConstructBSTFromPreorder {

    public static void main(String[] args) {
        int[] arr = {8, 3, 1, 6, 4, 7, 10, 14, 13};
        bstFromPreorder(arr);
    }

    public TreeNode bstFromPreorderRecursive(int[] preorder) {
        return helper(preorder, 0, preorder.length - 1);
    }

    private TreeNode helper(int[] preorder, int start, int end) {
        if (start > end) return null;

        TreeNode node = new TreeNode(preorder[start]);
        int i;
        // 8, 3, 1, 6, 4, 7, 10, 14, 13
        // Root is 8 (index 0)
        //The loop finds that 10 (index 6) is the first element greater than 8
        //So, elements from index 1 to 5 form the left subtree, and 6 to 8 form the right subtree
        //
        //For the left subtree {3, 1, 6, 4, 7}:
        //Root is 3 (index 1)
        //The loop finds that 6 (index 3) is the first element greater than 3
        //So, {1} forms the left subtree of 3, and {6, 4, 7} forms the right subtree
        for (i = start; i <= end; i++) {
            if (preorder[i] > node.val)
                break;
        }

        node.left = helper(preorder, start + 1, i - 1);
        node.right = helper(preorder, i, end);
        return node;


    }

    public static TreeNode bstFromPreorder(int[] preorder) {
        if (preorder == null || preorder.length == 0) {
            return null;
        }
        Deque<TreeNode> stack = new ArrayDeque<>();
        TreeNode root = new TreeNode(preorder[0]);
        stack.push(root);
        //8, 3, 1, 6, 4, 7, 10, 14, 13 
        for (int i = 1; i < preorder.length; i++) {
            TreeNode node = new TreeNode(preorder[i]);
            if (preorder[i] < stack.peek().val) {
                stack.peek().left = node;
            } else {
                TreeNode parent = stack.peek();
                while (!stack.isEmpty() && preorder[i] > stack.peek().val) {
                    parent = stack.pop();
                }
                parent.right = node;
            }
            stack.push(node);
        }
        return root;
    }

    static class TreeNode {
        TreeNode left;
        TreeNode right;
        int val;

        public TreeNode(int val) {
            this.val = val;
        }

        public String toString() {
            return val + "";
        }
    }
}

