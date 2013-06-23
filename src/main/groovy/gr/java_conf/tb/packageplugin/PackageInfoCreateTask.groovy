package gr.java_conf.tb.packageplugin
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

/**
 * Package-info.javaを出力するタスク.
 *
 * @author tbpgr
 *
 */
class PackageInfoCreateTask extends DefaultTask {
  def PACKAGE_INFO = 'package-info.java'
  String projectBasePath = null;
  String srcBasePath = null;
  String outputBasePath = null;

  @TaskAction
  def createPackageInfo() {
    if (this.projectBasePath == null | this.outputBasePath == null | this.srcBasePath == null)
      throw new PackageInfoCreateTaskException("projectBasePath or outputBasePath or srcBasePath is null. you must setting these parameter")

    def outputBasePath = new File(projectBasePath + outputBasePath)

    outputBasePath.eachDirRecurse {
      if (it == outputBasePath)
        return
      if (existsPackageInfo(it))
        return

      def packageName = getPackageName(it)
      def javaDoc = getPackageInfoJavaDoc(packageName)
      def writer = new File(it, PACKAGE_INFO).newWriter('UTF-8')
      writer.writeLine(javaDoc)
      writer.close()

      println("${it}/${PACKAGE_INFO}")
    }

    println "package-info output complete!"
  }

  def String getPackageName(File currentFolder) {
    def String currentFolderPath = currentFolder.toString()
    def rootPackageIndex = currentFolderPath.indexOf(srcBasePath)
    def packageName = currentFolderPath.substring(rootPackageIndex + srcBasePath.size() + 1, currentFolderPath.size())
    return packageName.replaceAll(/[\/|\\]/, '.')
  }

  def boolean existsPackageInfo(File file) {
    return new File(file, PACKAGE_INFO).exists()
  }

  def String getPackageInfoJavaDoc(String path) {
    return """\
/**
 * ${path} package.
 *
 * <pre>
 * // TODO input package description
 * </pre>
 *
 */
package ${path};
"""
  }

  class PackageInfoCreateTaskException extends Exception {
    PackageInfoCreateTaskException(String message) {
      super(message)
    }
  }
}