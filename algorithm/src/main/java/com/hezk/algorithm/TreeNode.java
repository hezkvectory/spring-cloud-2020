package com.hezk.algorithm;

import java.util.List;
import java.util.ArrayList;
import java.io.Serializable;

public class TreeNode implements Serializable {
    private int parentId;
    private int selfId;
//    protected String nodeName;
//    protected Object obj;
    protected TreeNode parentNode;
    protected List<TreeNode> childList;


    public static void main(String[] args) {
        TreeNode treeNode00 = new TreeNode(0, 0);

        TreeNode treeNode11 = new TreeNode(0, 11);
        TreeNode treeNode12 = new TreeNode(0, 12);
        TreeNode treeNode13 = new TreeNode(0, 13);
        TreeNode treeNode14 = new TreeNode(0, 14);

        treeNode00.getChildList().add(treeNode11);
        treeNode00.getChildList().add(treeNode12);
        treeNode00.getChildList().add(treeNode13);
        treeNode00.getChildList().add(treeNode14);

        TreeNode treeNode111 = new TreeNode(1, 111);
        TreeNode treeNode112 = new TreeNode(1, 112);
        TreeNode treeNode113 = new TreeNode(1, 113);

        treeNode11.getChildList().add(treeNode111);
        treeNode11.getChildList().add(treeNode112);
        treeNode11.getChildList().add(treeNode113);


        TreeNode treeNode121 = new TreeNode(2, 121);
        TreeNode treeNode122 = new TreeNode(2, 122);

        treeNode12.getChildList().add(treeNode121);
        treeNode12.getChildList().add(treeNode122);

        TreeNode treeNode131 = new TreeNode(3, 131);
        treeNode13.getChildList().add(treeNode131);


        TreeNode treeNode141 = new TreeNode(4, 141);
        TreeNode treeNode142 = new TreeNode(4, 142);
        treeNode14.getChildList().add(treeNode141);
        treeNode14.getChildList().add(treeNode142);


        treeNode00.traverse();

    }

    public TreeNode(int parentId, int selfId){
        this.parentId = parentId;
        this.selfId = selfId;
        initChildList();
    }

    public TreeNode() {
        initChildList();
    }

    public TreeNode(TreeNode parentNode) {
        this.getParentNode();
        initChildList();
    }

    public boolean isLeaf() {
        if (childList == null) {
            return true;
        } else {
            if (childList.isEmpty()) {
                return true;
            } else {
                return false;
            }
        }
    }

    /* 插入一个child节点到当前节点中 */
    public void addChildNode(TreeNode treeNode) {
        initChildList();
        childList.add(treeNode);
    }

    public void initChildList() {
        if (childList == null)
            childList = new ArrayList<TreeNode>();
    }

    public boolean isValidTree() {
        return true;
    }

    /* 返回当前节点的父辈节点集合 */
    public List<TreeNode> getElders() {
        List<TreeNode> elderList = new ArrayList<TreeNode>();
        TreeNode parentNode = this.getParentNode();
        if (parentNode == null) {
            return elderList;
        } else {
            elderList.add(parentNode);
            elderList.addAll(parentNode.getElders());
            return elderList;
        }
    }

    /* 返回当前节点的晚辈集合 */
    public List<TreeNode> getJuniors() {
        List<TreeNode> juniorList = new ArrayList<TreeNode>();
        List<TreeNode> childList = this.getChildList();
        if (childList == null) {
            return juniorList;
        } else {
            int childNumber = childList.size();
            for (int i = 0; i < childNumber; i++) {
                TreeNode junior = childList.get(i);
                juniorList.add(junior);
                juniorList.addAll(junior.getJuniors());
            }
            return juniorList;
        }
    }

    /* 返回当前节点的孩子集合 */
    public List<TreeNode> getChildList() {
        return childList;
    }

    /* 删除节点和它下面的晚辈 */
    public void deleteNode() {
        TreeNode parentNode = this.getParentNode();
        int id = this.getSelfId();

        if (parentNode != null) {
            parentNode.deleteChildNode(id);
        }
    }

    /* 删除当前节点的某个子节点 */
    public void deleteChildNode(int childId) {
        List<TreeNode> childList = this.getChildList();
        int childNumber = childList.size();
        for (int i = 0; i < childNumber; i++) {
            TreeNode child = childList.get(i);
            if (child.getSelfId() == childId) {
                childList.remove(i);
                return;
            }
        }
    }

    /* 动态的插入一个新的节点到当前树中 */
    public boolean insertJuniorNode(TreeNode treeNode) {
        int juniorParentId = treeNode.getParentId();
        if (this.parentId == juniorParentId) {
            addChildNode(treeNode);
            return true;
        } else {
            List<TreeNode> childList = this.getChildList();
            int childNumber = childList.size();
            boolean insertFlag;

            for (int i = 0; i < childNumber; i++) {
                TreeNode childNode = childList.get(i);
                insertFlag = childNode.insertJuniorNode(treeNode);
                if (insertFlag == true)
                    return true;
            }
            return false;
        }
    }

    /* 找到一颗树中某个节点 */
    public TreeNode findTreeNodeById(int id) {
        if (this.selfId == id)
            return this;
        if (childList.isEmpty() || childList == null) {
            return null;
        } else {
            int childNumber = childList.size();
            for (int i = 0; i < childNumber; i++) {
                TreeNode child = childList.get(i);
                TreeNode resultNode = child.findTreeNodeById(id);
                if (resultNode != null) {
                    return resultNode;
                }
            }
            return null;
        }
    }

    /* 遍历一棵树，层次遍历 */
    public void traverse() {
        if (selfId < 0)
            return;
        print(this.selfId);
        if (childList == null || childList.isEmpty())
            return;
        int childNumber = childList.size();
        for (int i = 0; i < childNumber; i++) {
            TreeNode child = childList.get(i);
            child.traverse();
        }
    }

    public void print(String content) {
        System.out.println(content);
    }

    public void print(int content) {
        System.out.println(String.valueOf(content));
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public int getSelfId() {
        return selfId;
    }

    public void setSelfId(int selfId) {
        this.selfId = selfId;
    }

    public TreeNode getParentNode() {
        return parentNode;
    }

    public void setParentNode(TreeNode parentNode) {
        this.parentNode = parentNode;
    }

    public void setChildList(List<TreeNode> childList) {
        this.childList = childList;
    }
}