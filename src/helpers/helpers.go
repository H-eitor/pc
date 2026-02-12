package helpers

import "sync"

type FileInfo struct {
	FileHashes []string
}

type IPStorage struct {
	data map[string]FileInfo
	mu   sync.RWMutex
}

func NewIPStorage() *IPStorage {
	return &IPStorage{
		data: make(map[string]FileInfo),
	}
}

func (s *IPStorage) AddClientInfo(ip string, fileInfo FileInfo) {
	s.mu.Lock()
	defer s.mu.Unlock()

	if existingInfo, exists := s.data[ip]; exists {
		existingInfo.FileHashes = append(existingInfo.FileHashes, fileInfo.FileHashes...)
		s.data[ip] = existingInfo
	} else {
		s.data[ip] = fileInfo
	}
}

func (s *IPStorage) GetClientsByHash(fileHash string) []string {
	s.mu.RLock()
	defer s.mu.RUnlock()

	var clients []string
	for ip, info := range s.data {
		for _, hash := range info.FileHashes {
			if hash == fileHash {
				clients = append(clients, ip)
				break
			}
		}
	}
	return clients
}

func (s *IPStorage) RemoveClient(ip string) {
	s.mu.Lock()
	defer s.mu.Unlock()

	delete(s.data, ip)
}
