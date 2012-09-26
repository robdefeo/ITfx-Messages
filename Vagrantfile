# -*- mode: ruby -*-
# vi: set ft=ruby :

Vagrant::Config.run do |config|

  # Every Vagrant virtual environment requires a box to build off of.
    config.vm.box = "centos-6.box"

    config.vm.customize ["modifyvm", :id, "--memory", 1024]

  # The url from where the 'config.vm.box' box will be fetched if it
  # doesn't already exist on the user's system.
    config.vm.box_url = "https://vagrant-centos-6.s3.amazonaws.com/centos-6.box"

    config.vm.forward_port 8080, 5080
    config.vm.forward_port 8000, 5081
    config.vm.forward_port 27017, 27017 # mongodb

  # Share an additional folder to the guest VM. The first argument is
  # an identifier, the second is the path on the guest to mount the
  # folder, and the third is the path on the host to the actual folder.
  # config.vm.share_folder "v-data", "/vagrant_data", "../data"


Vagrant::Config.run do |config|
  config.vm.provision :chef_solo do |chef|
    # This path will be expanded relative to the project directory
    chef.cookbooks_path = "../chef-repo/cookbooks"
    chef.add_recipe("java")
    chef.add_recipe("tomcat")
    chef.add_recipe("maven")
    chef.add_recipe("mongodb::10gen_repo")
    chef.add_recipe("splunk::forwarder")

    # chef.add_recipe("build-essential")
    chef.json = {
      :tomcat => {
        :java_options => '-Xmx128M -Djava.awt.headless=true -Xdebug -Xrunjdwp:transport=dt_socket,address=8000,server=y,suspend=n '
      },
      :splunk => {
                    :forwarder_version => "4.3.2",
                    :forwarder_build => "123586"# ,
                   # :indexer_name => "LONC02HF2J8DV7N.sea.corp.expecn.com"
                      }
    }
  end
end

end
