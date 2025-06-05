import React, { useState } from 'react';
import { BrowserRouter as Router, Routes, Route, Link, Navigate } from 'react-router-dom';
import { AppBar, Toolbar, Typography, Container, Button } from '@mui/material';
import UploadFileIcon from '@mui/icons-material/UploadFile';
import CreateFlashcardCollection from './components/CreateFlashcardCollection';
import FlashcardCollection from './components/FlashcardCollection';
import FlashcardCollectionList from './components/FlashcardCollectionList';
import FileUploadDialog from './components/FileUploadDialog';
import FilesPage from './pages/FilesPage';
import { FileResponse } from './services/fileService';

function App() {
  const [isUploadDialogOpen, setIsUploadDialogOpen] = useState(false);

  const handleUploadSuccess = (files: FileResponse[]) => {
    console.log('Files uploaded successfully:', files);
    setIsUploadDialogOpen(false);
    // You can add additional handling here
  };

  return (
    <Router>
      <AppBar position="static">
        <Toolbar>
          <Typography variant="h6" component="div" sx={{ flexGrow: 1 }}>
            Flashcard App
          </Typography>
          <Button 
            color="inherit" 
            component={Link} 
            to="/files"
            startIcon={<UploadFileIcon />}
          >
            Files
          </Button>
          <Button color="inherit" component={Link} to="/">
            My Collections
          </Button>
          <Button color="inherit" component={Link} to="/create">
            Create Collection
          </Button>
        </Toolbar>
      </AppBar>

      <Container>
        <Routes>
          <Route path="/create" element={<CreateFlashcardCollection />} />
          <Route path="/collection/:id" element={<FlashcardCollection />} />
          <Route path="/files" element={<FilesPage />} />
          <Route path="/" element={<FlashcardCollectionList />} />
          {/* Catch-all route for API calls */}
          <Route path="/api/*" element={null} />
          {/* Catch-all route for unknown paths */}
          <Route path="*" element={<Navigate to="/" replace />} />
        </Routes>
      </Container>

      <FileUploadDialog
        open={isUploadDialogOpen}
        onClose={() => setIsUploadDialogOpen(false)}
        onUploadSuccess={handleUploadSuccess}
      />
    </Router>
  );
}

export default App;
