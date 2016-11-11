/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.netmng.websvc.soap.parser.intra.OWLTypes;

/**
 *
 * @author TSOC
 */
public class CandidatePath {
    private NE_PathType path;
    private int residualBandwidth_AtoB;
    private int residualBandwidth_BtoA;
    private int residualBandwidth_Min;
    
    public CandidatePath() {
        path = new NE_PathType();
        residualBandwidth_AtoB = 99999999;
        residualBandwidth_BtoA = 99999999;
        residualBandwidth_Min = 99999999;
    }

    public int getResidualBandwidth_AtoB() {
        return residualBandwidth_AtoB;
    }

    public int getResidualBandwidth_BtoA() {
        return residualBandwidth_BtoA;
    }

    public int getResidualBandwidth_Min() {
        return residualBandwidth_Min;
    }

    public void setResidualBandwidth_AtoB(int residualBandwidth_AtoB) {
        this.residualBandwidth_AtoB = residualBandwidth_AtoB;
    }

    public void setResidualBandwidth_BtoA(int residualBandwidth_BtoA) {
        this.residualBandwidth_BtoA = residualBandwidth_BtoA;
    }

    public void setResidualBandwidth_Min(int residualBandwidth_Min) {
        this.residualBandwidth_Min = residualBandwidth_Min;
    }
    
    public void setPath(NE_PathType path) {
        this.path = path;
    }

    public NE_PathType getPath() {
        return path;
    }    
}
