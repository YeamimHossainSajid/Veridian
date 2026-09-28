variable "environment" {
  description = "Deployment environment (e.g. prod, staging, dev)"
  type        = string
  default     = "prod"
}

variable "region" {
  description = "AWS region for deployment"
  type        = string
  default     = "us-east-1"
}

variable "cluster_name" {
  description = "EKS cluster name"
  type        = string
  default     = "veridian-eks-prod"
}

variable "node_instance_types" {
  description = "EC2 instance types for compute nodes optimized for network performance"
  type        = list(string)
  default     = ["c6i.2xlarge", "c6i.4xlarge"]
}
