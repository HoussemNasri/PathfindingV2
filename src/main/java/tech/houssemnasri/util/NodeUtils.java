package tech.houssemnasri.util;

import tech.houssemnasri.api.node.INode;

public class NodeUtils {
    public static boolean isWalkable(INode node){
        return node.getType() != INode.Type.WALL;
    }
}
