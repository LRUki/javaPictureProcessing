package picture;

import java.util.Arrays;

public class Main {

  public static void main(String[] args) {
    Picture transformed = invokeMethod(args);
    Utils.savePicture(transformed,args[args.length - 1]);
    }

    private static Picture invokeMethod(String[] args){
    Picture picture;
    Process process;
    String transMethod = args[0];
    Picture ans = Utils.createPicture(1,1);

    if (transMethod.equals("blend")){
      String[] picNames = Arrays.copyOfRange(args,1,args.length - 1);
      Picture[] pictures = new Picture[picNames.length];
      for (int i = 0; i < picNames.length; i++){
        pictures[i] = Utils.loadPicture(picNames[i]);
      }

      process = new Process(pictures);
      process.blend();
      return process.getPicture();
    }else{
      int index = getFilePathIndex(transMethod);
      picture = Utils.loadPicture(args[index]);
      process = new Process(picture);

    }

      switch (transMethod){
        case("invert"):
          process.invert();
          break;

        case("grayscale"):
          process.grayscale();
          break;

        case("blur"):
          process.blur();
          break;

        case("rotate"):
          process.rotate(args[1]);
          break;

        case("flip"):
          process.flip(args[1]);
          break;

        default:
          break;
      }


      return process.getPicture();


    }




    private static int getFilePathIndex(String transMethod){
      if (transMethod.equals("rotate") || transMethod.equals("flip")){
        return 2;
      }else{
        return 1;
      }
    }
}
