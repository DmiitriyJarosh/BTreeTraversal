cmake_minimum_required(VERSION 3.15)
project(boostDFS)

set(CMAKE_CXX_STANDARD 14)

find_package(Boost 1.40 COMPONENTS program_options REQUIRED)

add_executable(boostDFS main.cpp)

target_link_libraries (boostDFS Boost::program_options)