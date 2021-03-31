package com.hezk.algorithm.tree;

//多叉树
public class ManyNodeTree {
    // 树根
    private ManyTreeNode root;

    // 构造函数
    public ManyNodeTree() {
        root = new ManyTreeNode();
        root.getData().setNodeName("root");
    }

    // 构造函数
    public ManyNodeTree(int key) {
        root = new ManyTreeNode();
        root.getData().setKey(key);
        root.getData().setNodeName("root");
    }

    public ManyTreeNode getRoot() {
        return root;
    }

    public void setRoot(ManyTreeNode root) {
        this.root = root;
    }

    // 遍历多叉树
    public String iteratorTree(ManyTreeNode treeNode) {

        StringBuilder sb = new StringBuilder();

        if (treeNode != null) {

            if ("root".equals(treeNode.getData().getNodeName())) {
                sb.append(treeNode.getData().getKey() + ",");
            }

            for (ManyTreeNode index : treeNode.getChildList()) {

                sb.append(index.getData().getKey() + ",");

                if (index.getChildList() != null && index.getChildList().size() > 0) {

                    sb.append(iteratorTree(index));

                }
            }
        }

        return sb.toString();
    }

    // 构造多叉树
    public static ManyNodeTree createTree() {

        // 用构造函数指定根节点的值
        ManyNodeTree tree = new ManyNodeTree(10);

        // 第一层的节点
        ManyTreeNode node20 = new ManyTreeNode(20);
        ManyTreeNode node30 = new ManyTreeNode(30);
        ManyTreeNode node40 = new ManyTreeNode(40);

        tree.getRoot().getChildList().add(0, node20);
        tree.getRoot().getChildList().add(1, node30);
        tree.getRoot().getChildList().add(2, node40);

        // 第二层的节点
        ManyTreeNode node50 = new ManyTreeNode(50);
        ManyTreeNode node60 = new ManyTreeNode(60);
        ManyTreeNode node70 = new ManyTreeNode(70);
        ManyTreeNode node80 = new ManyTreeNode(80);
        ManyTreeNode node90 = new ManyTreeNode(90);
        ManyTreeNode node100 = new ManyTreeNode(100);

        tree.getRoot().getChildList().get(0).getChildList().add(0, node50);
        tree.getRoot().getChildList().get(0).getChildList().add(1, node60);
        tree.getRoot().getChildList().get(0).getChildList().add(2, node70);

        tree.getRoot().getChildList().get(1).getChildList().add(0, node80);
        tree.getRoot().getChildList().get(1).getChildList().add(1, node90);

        tree.getRoot().getChildList().get(2).getChildList().add(0, node100);

        // 第三层的节点
        ManyTreeNode node110 = new ManyTreeNode(110);
        ManyTreeNode node120 = new ManyTreeNode(120);
        ManyTreeNode node130 = new ManyTreeNode(130);
        ManyTreeNode node140 = new ManyTreeNode(140);

        tree.getRoot().getChildList().get(0).getChildList().get(0).getChildList().add(0, node110);
        tree.getRoot().getChildList().get(0).getChildList().get(0).getChildList().add(1, node120);
        tree.getRoot().getChildList().get(0).getChildList().get(0).getChildList().add(2, node130);

        tree.getRoot().getChildList().get(0).getChildList().get(2).getChildList().add(0, node140);

        return tree;

    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        ManyNodeTree testTree = ManyNodeTree.createTree();
        String result = testTree.iteratorTree(testTree.getRoot());
        System.out.println(result);
    }

}