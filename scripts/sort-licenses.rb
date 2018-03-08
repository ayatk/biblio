#!/usr/bin/env ruby

require 'yaml'

data = YAML.load_file("app/licenses.yml")
data.sort_by! { |licenses| licenses['artifact']}
YAML.dump(data, File.open('app/licenses.yml', 'w'))
