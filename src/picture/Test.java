package picture;

public class Test {
    public static void main (String[] args){
        String[] tst1 = {"blur","images/sunset64x32.png","../../../test/test.png"};
        String[] tst2 ={"blur","images/bwpatterns64x64.png","../../../test/test2.png"};
        String[] tst3 ={"flip","H","images/green64x64doc.png","images/green64x64FHdoc.png"};
        String[] tst4 ={"rotate","32","images/green64x64doc.png","../../../test/test4.png"};
        String[] tst5 ={"blend","images/bwpatterns64x64.png","images/rainbow64x64doc.png","../../../test/test5.png"};
        String[] tst6 ={"mosaic","1", "images/black64x64.png","images/white64x64.png","../../../test/test11.png"};
        String[] tst7 ={"mosaic","1", "images/black64x64.png","images/white64x64.png","../../../test/test14.png"};
        String[] tst8 ={"mosaic","3", "images/spec1.png","images/spec2.png","../../../test/specMosaic.png"};
        String[] tst9 ={"mosainc","3", "images/white64x64.png","images/black64x64.png","images/rainbow64x64doc.png","../../../test/WBRainbow.png"};
        Main.main(tst9);





    }
}
