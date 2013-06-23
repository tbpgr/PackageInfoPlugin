package gr.java_conf.tb.packageplugin

import org.gradle.api.Plugin
import org.gradle.api.Project

/**
 * PackageInfoJavaを生成するPlugin.
 *
 * @author tbpgr
 *
 */
class PackageInfoPlugin implements Plugin<Project> {

  @Override
  public void apply(Project target) {
    target.task('createPackageInfo', type: PackageInfoCreateTask)
  }
}
