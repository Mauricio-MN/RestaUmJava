/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package board;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import testexceptions.TestUnexpectedStringParameter;
import vectorutils.Simetry;

public class Board {
    
    public int countRodadas = 0;
    public int countMoves = 0;
    private String[] typeTable;

    public static void main(String[] args) {

        Scanner sc=new Scanner(System.in);  

        Board Mesa = new Board();

        int[][] emptyList = new int[1][2];

        emptyList[0] = new int[]{3,3};

        int[][] moveList = new int[0][0];


        int typeTableN = -1;
        while(typeTableN < 0 || typeTableN > Mesa.typeTable.length - 1){
            System.out.println("Selecione o formato do jogo: ");
            for(int i =0; i < Mesa.typeTable.length; i++){
                System.out.println(i + " para " + Mesa.typeTable[i]);
            }
            typeTableN = sc.nextInt();
        }

        Mesa.startBoard(typeTableN);

        int pieceCount = 0;
        while(pieceCount < 1 || pieceCount > Mesa.fullPiecesCount){
            System.out.println("Quantidade de peças restantes (> 0, 1 para ganhar): ");
            pieceCount = sc.nextInt();
        }

        int pieceCountStop = 0;
        while(pieceCountStop < 1 || pieceCountStop > Mesa.fullPiecesCount){
            System.out.println("Quantidade de peças a restar (> 0, o jogo para antes de ganhar): ");
            pieceCountStop = sc.nextInt();
        }

        char[] awns = new char[1];
        awns[0] = 'E';
        while(awns[0] != 's' && awns[0] != 'n'){
            System.out.println("Centrado? (s/n): ");
            awns = sc.next().toCharArray();
        }
        boolean centred = false;
        if(awns[0] == 's'){
            centred = true;
        }

        Mesa.allPossibilities(0, Mesa.fullPieces, pieceCount, pieceCountStop, centred, moveList);

        System.out.println(Mesa.countRodadas + " rodadas");
        System.out.println(Mesa.countMoves + " movimentos");

    }

    public Board() {
        board = new Piece[7][7];
        typeTable = new String[]{"padrão","full"};
    }

    public Piece[][] board;
    private int[][] fullPieces;
    private int fullPiecesCount;

    public void startBoard(int tableNumber) {

        int[][] table;

        switch (tableNumber) {
            case 0:
                table = new int[][]{ {0, 0, 1, 1, 1, 0, 0},
                                     {0, 0, 1, 1, 1, 0, 0},
                                     {1, 1, 1, 1, 1, 1, 1},
                                     {1, 1, 1, 2, 1, 1, 1},
                                     {1, 1, 1, 1, 1, 1, 1},
                                     {0, 0, 1, 1, 1, 0, 0},
                                     {0, 0, 1, 1, 1, 0, 0}
                                    };
                 fullPiecesCount = 32;
                 break;
            case 1:
                table = new int[][]{ {1, 1, 1, 1, 1, 1, 1},
                                     {1, 1, 1, 1, 1, 1, 1},
                                     {1, 1, 1, 1, 1, 1, 1},
                                     {1, 1, 1, 2, 1, 1, 1},
                                     {1, 1, 1, 1, 1, 1, 1},
                                     {1, 1, 1, 1, 1, 1, 1},
                                     {1, 1, 1, 1, 1, 1, 1}
                                    };
                 fullPiecesCount = 48;
                 break;
            default:
                table = new int[][]{ {0, 0, 1, 1, 1, 0, 0},
                                     {0, 0, 1, 1, 1, 0, 0},
                                     {1, 1, 1, 1, 1, 1, 1},
                                     {1, 1, 1, 2, 1, 1, 1},
                                     {1, 1, 1, 1, 1, 1, 1},
                                     {0, 0, 1, 1, 1, 0, 0},
                                     {0, 0, 1, 1, 1, 0, 0}
                                    };
                 fullPiecesCount = 32;
                 break;

        }

        fullPieces = new int[fullPiecesCount][2];

        int p = 0;

        for (int i = 0; i < 7; i++) {
            for (int c = 0; c < 7; c++) {
                board[i][c] = new Piece(table[i][c]);
                if(table[i][c] == 1){
                    fullPieces[p] = new int[]{i,c};
                    p++;
                }
            }
        }

    }

class PieceGroup{
public Piece full;
public Piece side;
public Piece sideM;
}

private PieceGroup movePiece(int x, int y, String moveToSide, boolean unMove){
    PieceGroup localPieces = new PieceGroup();
    try{
    TestUnexpectedStringParameter check = new TestUnexpectedStringParameter(moveToSide,new String[]{"left","right","up","down"});

    int sideX;
        sideX = x;
    int sideY;
        sideY = y;
    int sideMX;
        sideMX = x;
    int sideMY;
        sideMY = y;

    switch (moveToSide) {
        case "right":
             sideMX = x + 1;
             sideX = x + 2;
             break;
        case "left":
             sideMX = x - 1;
             sideX = x - 2;
             break;
        case "up":
             sideMY = y - 1;
             sideY = y - 2;
             break;
        case "down":
             sideMY = y + 1;
             sideY = y + 2;
             break;
        default:
             System.out.println("Something is wrong Board.movePiece");
             sideMX = x + 1;
             sideX = x + 2;
             break;

    }


    Piece sideM = board[sideMX][sideMY];
    Piece side = board[sideX][sideY];
    Piece full = board[x][y];

    localPieces.sideM = sideM;
    localPieces.side = side;
    localPieces.full = full;

    if(unMove == false){
        sideM.full = false;
        side.full = true;
        full.full = false;
    }else{
        sideM.full = true;
        side.full = false;
        full.full = true;
    }

    }catch(Exception ex){
        System.out.println(ex.getMessage());
        StringWriter sw = new StringWriter();
        ex.printStackTrace(new PrintWriter(sw));
        String stacktrace = sw.toString();
        System.out.println(stacktrace + "\n"); 
    }
    return localPieces;
}

    private void possibleMovesRegister(Queue<int[]> listCoordsXandY, Queue<String> listSideRightLeftUpDown, int x, int y){
            listCoordsXandY.add(new int[]{x + 1, y});
            listCoordsXandY.add(new int[]{x + 2, y});
            listSideRightLeftUpDown.add("right");

            listCoordsXandY.add(new int[]{x - 1, y});
            listCoordsXandY.add(new int[]{x - 2, y});
            listSideRightLeftUpDown.add("left");

            listCoordsXandY.add(new int[]{x, y - 1});
            listCoordsXandY.add(new int[]{x, y - 2});
            listSideRightLeftUpDown.add("up");

            listCoordsXandY.add(new int[]{x, y + 1});
            listCoordsXandY.add(new int[]{x, y + 2});
            listSideRightLeftUpDown.add("down");
    }

    private boolean isValidCoord(int x, int y){
        if(x < 7 && y < 7 && x >= 0 && y >= 0){
            return true;
        }
        return false;
    }


    private boolean allPossibilities(int pieceEats, int[][] fullList, int piecesEnd, int remains, boolean centred, int moveList[][]) {

        if (fullList.length <= 0) {
            return true;
        }
        if((fullPiecesCount - pieceEats) < piecesEnd){
            return true;
        }

        boolean checkMovesOnfullList = true;

        for(int i = 0; i < fullList.length; i++) {

            int[] full = fullList[i];
            int fullX = full[0];
            int fullY = full[1];

            Queue<int[]> pieceoffset = new LinkedList<>();
            Queue<String> pieceSide = new LinkedList<>();

            possibleMovesRegister(pieceoffset, pieceSide, fullX, fullY);

            int[] baseToSide = new int[]{fullX, fullY};
            
            while (!(pieceoffset.isEmpty())) {
                int[] sidecoordsM = pieceoffset.poll();
                int[] sidecoords = pieceoffset.poll();
                String sideString = pieceSide.poll();

                if (isValidCoord(sidecoordsM[0],sidecoordsM[1]) && isValidCoord(sidecoords[0],sidecoords[1])) {

                        Piece sideM = board[sidecoordsM[0]][sidecoordsM[1]];
                        Piece side = board[sidecoords[0]][sidecoords[1]];

                        if (sideM.valid == true && sideM.full == true) {
                            if (side.valid == true && side.full == false) {
                                checkMovesOnfullList = false;

                                movePiece(fullX,fullY,sideString,false);

                                int[][] moveListRec = new int[moveList.length + 1][4];
                                System.arraycopy(moveList, 0, moveListRec, 0, moveList.length);
                                moveListRec[moveListRec.length-1] = new int[]{fullX,fullY,sidecoords[0],sidecoords[1]};

                                int[][] fullListRec = new int[fullList.length - 1][2];

                                int r = 0;
                                for (int p = 0; p < fullList.length; p++) {
                                    if((!Arrays.equals(fullList[p], sidecoords)) && (!Arrays.equals(fullList[p], sidecoordsM)) && (!Arrays.equals(fullList[p], baseToSide))){
                                        fullListRec[r] = fullList[p];
                                        r++;
                                    }
                                }
                                r = fullListRec.length;

                                fullListRec[r-1] = new int[]{sidecoords[0], sidecoords[1]};

                                countMoves++;
                                boolean last = allPossibilities(pieceEats + 1, fullListRec, piecesEnd, remains, centred, moveListRec);
                                if (last == false) {
                                    return false;
                                }

                                movePiece(fullX,fullY,sideString,true);

                            }
                        }

                }

            }

        }

        if(checkMovesOnfullList){
            countRodadas++;
        }
        if ((fullPiecesCount - piecesEnd) == pieceEats && checkMovesOnfullList) {
            boolean center = false;
                if(piecesEnd == 1){
                    int[] coords = moveList[moveList.length - 1];
                    if(coords[2] == 3 && coords[3] == 3){
                        center = true;
                    }
                }
                Integer[][] checkVector = new Integer[7][7];
                for(int i = 0; i < board.length; i++){
                    for(int c = 0; c < board.length; c++){
                        checkVector[i][c] = board[i][c].full ? 1 : 0;
                    }
                }
                Simetry<Object> simetry = new Simetry<>(checkVector);

                center = simetry.verifySlashBackSlashSimetry();

            if(centred){
                if(!center){
                    return true;
                }
            } else {
                if(center){
                    return true;
                }
            }

            System.out.println("Sobrou " + piecesEnd);
            
            int t = 0;
            for(int[] item: moveList){
                if(t < (fullPiecesCount - remains)){
                    System.out.println(" X: " + item[0] + " Y: " + item[1] + " moveu para " + " X: " + item[2] + " Y: " + item[3]);
                }
                t++;
            }
            System.out.println(pieceEats + " peças capturadas");
            if(center){
                System.out.println("Centrado");
            }

            return false;
        }

        return true;

    }

}
