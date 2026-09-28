# Kubernetes Production Deployment Runbook

## Cluster Architecture

Veridian production clusters are provisioned on AWS EKS or bare-metal Kubernetes using Istio Service Mesh with mTLS enabled.

---

## 1. Apply Namespace & Configuration

```bash
kubectl apply -f infrastructure/k8s/namespace.yaml
kubectl apply -f infrastructure/k8s/configmap.yaml
```

---

## 2. Deploy Trading Services

```bash
kubectl apply -f infrastructure/k8s/api-gateway-deployment.yaml
kubectl apply -f infrastructure/k8s/order-service-deployment.yaml
kubectl apply -f infrastructure/k8s/matching-engine-deployment.yaml
kubectl apply -f infrastructure/k8s/risk-engine-deployment.yaml
kubectl apply -f infrastructure/k8s/ingress.yaml
```

---

## 3. Verify Pod Health and Resource Allocation

```bash
kubectl get pods -n veridian-prod -o wide
```

Ensure the matching engine is pinned to dedicated high-frequency compute nodes (`c6i.4xlarge`) with CPU affinity and zero CFS throttling.

---

## 4. Rollback Procedure
In case of critical regression:
```bash
kubectl rollout undo deployment/order-service -n veridian-prod
```
