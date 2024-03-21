import java.nio.file.Files
import java.nio.file.StandardCopyOption
import java.time.Duration
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

// This class provides methods for manipulating files, such as replacing text in files and creating backups
class FileManipulation {

    def replaceTextInFiles(String directoryPath, String originalText, String newText, String outputPath = null) {
        def modifiedFiles = []
        def startTime = LocalDateTime.now()

        // Check if the directory exists
        def directory = new File(directoryPath)
        if (!directory.isDirectory()) {
            println("Error: $directoryPath is not a directory")
            return
        }

        println("Start time: ${formatTime(startTime)}")


        // Iterate over all files in the directory and its subdirectories
        directory.eachFileRecurse() { file ->
            try {
                if (!file.isDirectory()) {
                    def text = file.text // Read the file content
                    if (text.contains(originalText)) {
                        backupFile(file) // Create a backup of the file

                        def newTextContent = text.replaceAll(originalText, newText)
                        file.text = newTextContent // Replace the text in the file
                        modifiedFiles.add(file.name) // Add the file name to the list of modified files
                        println("Replaced in file: ${file.absolutePath}")
                    }
                }
            } catch (Exception e) {
                println("Error processing file: ${file.absolutePath} - ${e.message}")
            }
        }

        def endTime = LocalDateTime.now()
        println("End time: ${formatTime(endTime)}")
        println("Total time taken: ${calculateTimeTaken(startTime, endTime)}")

        if (outputPath) {
            writeModifiedFilesList(outputPath, modifiedFiles)  // Write the list of modified files to the output file
        }
    }


    static void writeModifiedFilesList(String outputFile, List<String> modifiedFiles) {
        def outputFilePath = new File(outputFile)
        outputFilePath.text = modifiedFiles.join('\n')
        println("Files names which are modified are in : ${outputFilePath.absolutePath}")
    }

    // Create a backup of the file
    static void backupFile(File file) {
        try {
            def backupFile = new File("${file.absolutePath}.bak")
            Files.copy(file.toPath(), backupFile.toPath(), StandardCopyOption.REPLACE_EXISTING)
            println("Backup created: ${backupFile.absolutePath}")
        } catch (Exception e) {
            println("Error creating backup for file: ${file.absolutePath} - ${e.message}")
        }
    }

    // Format the LocalDateTime object to a string
    static String formatTime(LocalDateTime dateTime) {
        return dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
    }

    // Calculate the time taken between two LocalDateTime objects
    static String calculateTimeTaken(LocalDateTime startTime, LocalDateTime endTime) {
        def duration = Duration.between(startTime, endTime)
        def hours = duration.toHours()
        def minutes = duration.toMinutesPart()
        def seconds = duration.toSecondsPart()
        def millis = duration.toMillisPart()
        return "${hours}h ${minutes}m ${seconds}s ${millis}ms"
    }
}
