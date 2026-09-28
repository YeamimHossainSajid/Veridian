terraform {
  required_version = ">= 1.5.0"
  required_providers {
    aws = {
      source  = "hashicorp/aws"
      version = "~> 5.0"
    }
  }
}

provider "aws" {
  region = var.region
}

# VPC for low-latency financial trading workload
resource "aws_vpc" "veridian_vpc" {
  cidr_block           = "10.0.0.0/16"
  enable_dns_support   = true
  enable_dns_hostnames = true

  tags = {
    Name        = "veridian-vpc-${var.environment}"
    Environment = var.environment
    Platform    = "Veridian"
  }
}

# Subnets
resource "aws_subnet" "veridian_private_1" {
  vpc_id            = aws_vpc.veridian_vpc.id
  cidr_block        = "10.0.1.0/24"
  availability_zone = "${var.region}a"

  tags = {
    Name = "veridian-private-1a"
  }
}

resource "aws_subnet" "veridian_private_2" {
  vpc_id            = aws_vpc.veridian_vpc.id
  cidr_block        = "10.0.2.0/24"
  availability_zone = "${var.region}b"

  tags = {
    Name = "veridian-private-1b"
  }
}

# Security group for low-latency internal microservice communication
resource "aws_security_group" "internal_mesh_sg" {
  name        = "veridian-internal-mesh-sg"
  description = "Security group for internal low-latency microservice communication"
  vpc_id      = aws_vpc.veridian_vpc.id

  ingress {
    from_port   = 0
    to_port     = 0
    protocol    = "-1"
    self        = true
    description = "Allow all intra-cluster traffic"
  }

  egress {
    from_port   = 0
    to_port     = 0
    protocol    = "-1"
    cidr_blocks = ["0.0.0.0/0"]
  }

  tags = {
    Name = "veridian-internal-mesh-sg"
  }
}
