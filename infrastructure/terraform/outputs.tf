output "vpc_id" {
  description = "The ID of the Veridian VPC"
  value       = aws_vpc.veridian_vpc.id
}

output "private_subnets" {
  description = "Private subnets for backend microservices"
  value       = [aws_subnet.veridian_private_1.id, aws_subnet.veridian_private_2.id]
}

output "security_group_id" {
  description = "Internal service mesh security group ID"
  value       = aws_security_group.internal_mesh_sg.id
}
