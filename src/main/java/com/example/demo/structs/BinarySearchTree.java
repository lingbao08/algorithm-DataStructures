package com.example.demo.structs;

/**
 * @author lingbao08
 * @DESCRIPTION 这是一个关于二叉查找树的手工类
 * @create 2018/6/1 15:46
 **/

public class BinarySearchTree<AnyType> {

    private NodeTree<AnyType> root;

    public BinarySearchTree() {
        root = null;
    }

    public BinarySearchTree(NodeTree<AnyType> node) {
        root = node;
    }

    public void insert(AnyType t) {
        root = insert(t, root);
    }

    private NodeTree<AnyType> insert(AnyType t, NodeTree<AnyType> node) {
        if (root == null)
            return new NodeTree<AnyType>(t, null, null);
        int result = compareTo(t, node.element);
        //插入到右子树，若右子树节点为空，则直接赋值；若右子树不为空，则递归赋值。
        if (result > 0)
            if (null == node.right)
                node.right = new NodeTree<AnyType>(t, null, null);
            else
                insert(t, node.right);
        else if (result < 0)
            //插入到左子树，若左子树节点为空，则直接赋值；若左子树不为空，则递归赋值。
            if (null == node.left)
                node.left = new NodeTree<AnyType>(t, null, null);
            else
                insert(t, node.left);
        return node;
    }

    public BinarySearchTree<AnyType> remove(AnyType t) {
        root = remove(t, root);
        return new BinarySearchTree<AnyType>(root);
    }

    /**
     * 2016年8月31日10:45:32
     *
     * @author 08
     * 该删除方法是用 右子树的最小值 来替换要删除的对象，然后递归删除 要删除的对象 。
     */
    private NodeTree<AnyType> remove(AnyType t, NodeTree<AnyType> node) {
        if (root == null)
            return null;
        int result = compareTo(t, node.element);
        if (result < 0)
            node.left = remove(t, node.left);
        else if (result > 0)
            node.right = remove(t, node.right);
        else if (node.left != null && node.right != null) {
            node.element = findMin(node.right);
            node = remove((AnyType) node.element, node.right);
        } else {
            node = (node.left == null) ? node.right : node.left;
        }
        return node;

    }

    public AnyType findMin() {
        return findMin(root);
    }

    /**
     * @author 08
     * 2016年8月31日10:48:32
     * 一路向左，在不是null的前一个元素输出
     */
    private AnyType findMin(NodeTree<? extends AnyType> node) {
        if (node != null)
            while (node.left != null)
                node = node.left;
        return (AnyType) node.element;
    }

    public AnyType findMax() {
        return findMax(root);
    }

    /**
     * @author 08
     * 2016年8月31日10:48:32
     * 一路向右，在不是null的前一个元素输出
     */
    private AnyType findMax(NodeTree<? extends AnyType> node) {
        if (node != null)
            while (node.right != null)
                node = node.right;
        return (AnyType) node.element;
    }


    @SuppressWarnings("hiding")
    private class NodeTree<AnyType> {
        private AnyType element;
        private NodeTree<AnyType> left;
        private NodeTree<AnyType> right;

        public NodeTree(AnyType element, NodeTree<AnyType> left, NodeTree<AnyType> right) {
            super();
            this.element = element;
            this.left = left;
            this.right = right;
        }
    }

    private int compareTo(AnyType t1, AnyType t2) {
        if (t1 instanceof Integer || t1 instanceof Double || t1 instanceof Float) {
            return Integer.valueOf(t1.toString()) - Integer.valueOf(t2.toString());
        }
        char[] c1 = t1.toString().toCharArray();
        char[] c2 = t2.toString().toCharArray();
        int len1 = c1.length;
        int len2 = c1.length;
        int minlength = Math.min(len1, len2);
        int k = 0;
        while (k <= minlength) {
            if (c1[k] != c2[k]) {
                return c1[k] - c2[k];
            }
            k++;
        }
        return len1 - len2;
    }
}
