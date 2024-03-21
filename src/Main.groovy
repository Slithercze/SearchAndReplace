static void main(String[] args) {
  FileManipulation fileManipulation = new FileManipulation()

  /*
  Usage is pretty simple: method takes 3 required parameters and one optional parameter.
    The first parameter is the directory path where the files are located.
    The second parameter is the text that you want to replace.
    The third parameter is the new text that you want to replace with.
    The fourth parameter is the output file path where the list of modified files will be written.

    Edit the following variables to test the method:
   */

  String directory = "files"
  String textToReplace = "ahoj"
  String newText = "jablko"
  String outputPath = "files/output.txt" // Optional parameter, null if not needed


  fileManipulation.replaceTextInFiles(directory, textToReplace, newText, outputPath)
}
