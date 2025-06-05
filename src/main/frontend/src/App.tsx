import React from 'react';
import { BrowserRouter as Router, Routes, Route, Link } from 'react-router-dom';
import { AppBar, Toolbar, Typography, Container, Button } from '@mui/material';
import CreateFlashcardCollection from './components/CreateFlashcardCollection';
import FlashcardCollection from './components/FlashcardCollection';
import FlashcardCollectionList from './components/FlashcardCollectionList';

function App() {
  return (
    <Router>
      <AppBar position="static">
        <Toolbar>
          <Typography variant="h6" component="div" sx={{ flexGrow: 1 }}>
            Flashcard App
          </Typography>
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
          <Route path="/" element={<FlashcardCollectionList />} />
        </Routes>
      </Container>
    </Router>
  );
}

export default App;
