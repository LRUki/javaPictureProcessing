package picture;

import jdk.jshell.execution.Util;

import java.util.Arrays;

public class Main {

  public static void main(String[] args)  {
    try{
      Picture transformedPic = invokeMethod(args);
      String outputPath = args[args.length - 1];
      savePic(transformedPic, outputPath);
    }catch (Exception e){
      throw new IllegalArgumentException(e);
    }
    }

  /*******************helper functions ****************************/



  private static Picture invokeMethod(String[] args) throws Exception {
    Picture picture;
    Picture[] pictures;
    Process process;
    String transMethod = args[0];

    if (transMethod.equals("blend") || transMethod.equals("mosaic")){
      String[] picNames = (transMethod.equals("blend")) ? sliceArray(args,1,args.length) : sliceArray(args,2,args.length);
      pictures = stringToPicture(picNames);
      process = new Process(pictures);
    }else{
      String inputPath = args[getFilePathIndex(transMethod)];
      picture = Utils.loadPicture(inputPath);
      process = new Process(picture);
    }
    return transformPicture(transMethod, process, args);
  }

  //returns sliced array from start (inclusive), end (exclusive)
  private static String[] sliceArray(String[] args, int start, int end){
    return Arrays.copyOfRange(args, start, end - 1);
  }

  private static Picture[] stringToPicture(String[] str){
    return Arrays.stream(str).map(Utils::loadPicture).toArray(Picture[]::new);
  }


  private static int getFilePathIndex(String transMethod){
    if (transMethod.equals("rotate") || transMethod.equals("flip")){
      return 2;
    }else{
      return 1;
    }
  }

  private static Picture transformPicture(String transMethod, Process process, String[] args) throws Exception {
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

      case("blend"):
        process.blend();
        break;
      case("mosaic"):
        process.mosaic(args[1]);
        break;
      default:
        //Why is this not thrown on main?
        //Get NullPointerException instead of "Invalid method!" when misspelling the method name on purpose
        throw new Exception("Invalid method!");
    }

    return process.getPicture();
  }

  private static void savePic(Picture picture, String outputFile){
    Utils.savePicture(picture,outputFile);
  }
}
