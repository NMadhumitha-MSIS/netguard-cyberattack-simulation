/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package netguard;

import java.awt.Color;

/**
 * Node class to store the visual properties of nodes and its state in the
 * network
 */
public class Node {

    public int id;
    public int x, y;
    public Color color = Color.green;

    public boolean infected = false;
    public boolean isolated = false;

    public Node(int id, int x, int y) {
        this.id = id;
        this.x = x;
        this.y = y;
    }
}
