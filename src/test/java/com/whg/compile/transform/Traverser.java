package com.whg.compile.transform;

import com.whg.compile.ast.ASTNode;
import com.whg.compile.ast.ASTType;
import com.whg.compile.ast.CallExpression;
import com.whg.compile.ast.NumberLiteral;
import com.whg.compile.ast.transform.CalleeExpression;
import com.whg.compile.ast.transform.ExpressionStatement;

import java.util.EnumMap;
import java.util.List;

public class Traverser {

    private static final EnumMap<ASTType, ASTNodeHandler> handlerMap = new EnumMap<>(ASTType.class);
    static {
        handlerMap.put(ASTType.Program, new BaseASTNodeHandler());
        handlerMap.put(ASTType.CallExpression, new BaseASTNodeHandler(){
            @Override
            public void enter(ASTNode node, ASTNode newAst) {
                super.enter(node, newAst);
                ASTNode parent = node.parent();
                ASTNode expression = new CalleeExpression(parent, (CallExpression) node);
                if(parent.type() != ASTType.CallExpression){
                    expression = new ExpressionStatement(parent, (CalleeExpression) expression);
                }
                newAst.addNode(expression);
            }
        });
        handlerMap.put(ASTType.NumberLiteral, new BaseASTNodeHandler(){
            @Override
            public void enter(ASTNode node, ASTNode newAst) {
                super.enter(node, newAst);
                newAst.addNode(new NumberLiteral(newAst, (Number) node.value()));
            }
        });
    }

    private final ASTNode newAst;

    public Traverser(ASTNode newAst) {
        this.newAst = newAst;
    }

    public void traverse(ASTNode ast){
        traverseNode(ast);
    }

    public void traverseList(List<ASTNode> list){
        list.forEach(node -> traverseNode(node));
    }

    private void traverseNode(ASTNode node){
        ASTType type = node.type();
        ASTNodeHandler handler = handlerMap.get(type);
        if(handler != null){
            handler.enter(node, newAst);
        }

        node.traverse(this);

        if(handler != null){
            handler.exist(node, newAst);
        }
    }

}
