package pl.agilevision.hardware.um7.data.parser;

/**
 * Created by volodymyr on 02.12.16.
 */
public final class ParserUtils {
  private ParserUtils(){
    // EMPTY
  }

  public static boolean beginsWith(final byte[] haystack, final byte[] needle){
    for(int i = 0; i < needle.length; i++){
      if (haystack[i] != needle[i]){
          return false;
      }
    }

    return true;
  }
}
